package poly.edu.ka.controller.admin;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import poly.edu.ka.domain.Category;
import poly.edu.ka.domain.Product;
import poly.edu.ka.model.CategoryDto;
import poly.edu.ka.model.ProductDto;
import poly.edu.ka.repository.ProductRepository;
import poly.edu.ka.services.AccountService;
import poly.edu.ka.services.CategoryService;
import poly.edu.ka.services.ProductService;
import poly.edu.ka.services.StorageService;

@Controller
@RequestMapping("admin/products")
public class ProductController {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	CategoryService categoryService;
	@Autowired
	AccountService accountService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	StorageService storageService;
	
	@ModelAttribute("categories")
	public List<CategoryDto> getCategoryDtos()
	{
		return categoryService.findAll().stream().map(item->{
			CategoryDto dto = new CategoryDto();
			BeanUtils.copyProperties(item, dto);
			return dto;
		}).toList();
	}
	
	
	@GetMapping("add")
	public String add(Model model) {
		ProductDto dto = new ProductDto();
		dto.setIsEdit(false);
		model.addAttribute("product", dto);
		return "admin/products/newOrEdit";
	}
	
	@GetMapping("edit/{productId}")
	public ModelAndView edit(ModelMap model, @PathVariable("productId")Long productId) {
		Optional<Product> opt = productService.findById(productId);
		ProductDto dto = new ProductDto();
		if(opt.isPresent())
		{
			Product entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setCategoryId(entity.getCategory().getCategoryId());
			dto.setIsEdit(true);
			model.addAttribute("product", dto);
			return new ModelAndView( "admin/products/newOrEdit",model);
		}
		model.addAttribute("message", "Product is not existed!");
		
		return new ModelAndView( "redirect:/admin/products",model);

	}
	
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("product") ProductDto dto, BindingResult result) {
		if(result.hasErrors())
		{
			return new ModelAndView("admin/products/newOrEdit");

		}
		Product entity = new Product();
		
		BeanUtils.copyProperties(dto, entity);
		 Category category = new Category();
		 category.setCategoryId(dto.getCategoryId());
		 entity.setCategory(category);
		 if(!dto.getImageFile().isEmpty())
		 {
			 UUID uuid = UUID.randomUUID();
			 String uuString = uuid.toString();
			 entity.setImage(storageService.getStoredFilename(dto.getImageFile(), uuString));
			 storageService.store(dto.getImageFile(), entity.getImage());
		 }
		productService.save(entity);
		
		model.addAttribute("message", "Product is saved!");
		
		return new ModelAndView("forward:/admin/products",model);
	}
	
	@GetMapping("images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename)
	{
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION	, "attachment;filename=\"" +file.getFilename() +"\"").body(file);
	}
	@GetMapping("delete/{productId}")
	public ModelAndView delete(ModelMap model, @PathVariable("productId")Long productId) throws IOException {
		
		Optional<Product> opt = productService.findById(productId);
		if(opt.isPresent())
		{
			if(!StringUtils.isEmpty(opt.get().getImage()))
			{
				storageService.delete(opt.get().getImage());
			}
			productService.delete(opt.get());
			model.addAttribute("message", "Product is deleted!");
		}else
		{
			model.addAttribute("message", "Product is not found!");
		}
		
		return new ModelAndView( "forward:/admin/products",model);
	}
	
	@RequestMapping("")
	public String list(ModelMap model,@RequestParam Optional<String> message, @PageableDefault(size = 5, sort = "name", direction = Direction.ASC)Pageable pageable) {
//		List<Product> list = productService.findAll();
		Page<Product> pages = productRepository.findAll(pageable);
		if(message.isPresent())
		{
			model.addAttribute("message", message.get());
		}
		//tiêu chí sắp xếp
		List<Sort.Order> sortOrders = pageable.getSort().stream().collect(Collectors.toList());
		if(sortOrders.size() > 0)
		{
			Sort.Order order = sortOrders.get(0);
			model.addAttribute("sort", order.getProperty() + "," + (order.getDirection() ==  Sort.Direction.ASC?"asc":"desc" ));
		}else
		{
			//nếu chưa có tiêu chí thì sắp xếp theo kiểu ngầm định
			model.addAttribute("sort", "name, asc");
		}
		model.addAttribute("pages", pages);
//		model.addAttribute("products", list);
		return "admin/products/list";
	}
	
	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name = "name", required = false)String name) {
		List<Category> list = null;
		if(StringUtils.hasText(name))
		{
			list = categoryService.findByNameContaining(name);
		}
		else
		{
			list = categoryService.findAll();
		}
		model.addAttribute("products", list);
		
		return "admin/products/search";
	}
	@GetMapping("searchpaginated")
	public String search(ModelMap model,
			@RequestParam(name = "name", required = false)String name,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size
			) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		Pageable pageable = PageRequest.of(currentPage-1, pageSize,Sort.by("name"));
		Page<Category> resultPage = null;

		if(StringUtils.hasText(name))
		{
			resultPage = categoryService.findByNameContaining(name,pageable);
			model.addAttribute("name", name);
		}
		else
		{
			resultPage = categoryService.findAll(pageable);
		}
		int totalPages = resultPage.getTotalPages();
		if(totalPages > 0 )
		{
			int start = Math.max(1, currentPage -2);
			int end = Math.min(currentPage + 2, totalPages);
			if(totalPages > 5)
			{
				if(end == totalPages)
					{
						start = end -5;
					}
				else if(start == 1)
				{
					end = start + 5;
				}
				
			}
				List<Integer> pageNumbers = IntStream.range(start, end)
						.boxed().collect(Collectors.toList());
				model.addAttribute("pageNumbers", pageNumbers);
		}
		model.addAttribute("categoryPage", resultPage);
		
		return "admin/products/searchpaginated";
	}
}
