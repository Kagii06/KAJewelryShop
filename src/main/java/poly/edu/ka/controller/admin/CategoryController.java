package poly.edu.ka.controller.admin;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.SortDefault;
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
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import poly.edu.ka.domain.Account;
import poly.edu.ka.domain.Category;
import poly.edu.ka.model.CategoryDto;
import poly.edu.ka.repository.AccountRepository;
import poly.edu.ka.repository.CategoryRepository;
import poly.edu.ka.services.CategoryService;

@Controller
@RequestMapping("admin/categories")
public class CategoryController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("category", new CategoryDto());
		return "admin/categories/newOrEdit";
	}
	
	@GetMapping("edit/{categoryId}")
	public ModelAndView edit(ModelMap model, @PathVariable("categoryId")Long categoryId) {
		Optional<Category> opt = categoryService.findById(categoryId);
		CategoryDto dto = new CategoryDto();
		if(opt.isPresent())
		{
			Category entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			model.addAttribute("category", dto);
			return new ModelAndView( "admin/categories/newOrEdit",model);
		}
		model.addAttribute("message", "Category is not existed!");
		return new ModelAndView( "redirect:/admin/categories",model);

	}
	
	@PostMapping("newOrEdit")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("category") CategoryDto dto, BindingResult result) {
		if(result.hasErrors())
		{
			return new ModelAndView("admin/categories/newOrEdit");

		}
		Category entity = new Category();
		
		BeanUtils.copyProperties(dto, entity);
		 
		categoryService.save(entity);
		
		model.addAttribute("message", "Category is saved!");		
		return new ModelAndView("forward:/admin/categories",model);
	}
	
	@GetMapping("delete/{categoryId}")
	public ModelAndView delete(ModelMap model, @PathVariable("categoryId")Long categoryId) {
		
		categoryService.deleteById(categoryId);
		model.addAttribute("message", "Category is deleted!");
		return new ModelAndView( "forward:/admin/categories",model);
	}
	
	@RequestMapping("")
	public String list(ModelMap model) {
		List<Category> list = categoryService.findAll();
		model.addAttribute("categories", list);
		return "admin/categories/list";
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
		model.addAttribute("categories", list);
		
		return "admin/categories/search";
	}
	@GetMapping("searchpaginated")
	public String search(ModelMap model,
			@RequestParam(name = "name", required = false)String name,
			@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size
			) {
		int currentPage = page.orElse(1);
		int pageSize = size.orElse(5);
		Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.by(Sort.Order.asc("categoryId")));
		Page<Category> resultPage = null;
		Page<Category> pages = categoryRepository.findAll(pageable);

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
//		List<Sort.Order> sortOrders = pages.getSort().stream().collect(Collectors.toList());
//		if(sortOrders.size() > 0)
//		{
//			Sort.Order order = sortOrders.get(0);
//			model.addAttribute("sort", order.getProperty() + "," + (order.getDirection() == Sort.Direction.ASC?"asc": "desc"));
//		}else
//		{
//			model.addAttribute("sort", "name,asc");
//		}
		
		model.addAttribute("categoryPage", resultPage);
		model.addAttribute("pages", pages);
		return "admin/categories/searchpaginated";
	}
	@GetMapping("sort")
	public String sort(ModelMap model,@RequestParam Optional<String> message, @SortDefault(sort = "name", direction = Direction.ASC) Sort sort)
	{
		Iterable<Category> list = categoryRepository.findAll(sort);
		
		if(message.isPresent())
		{
			model.addAttribute("message", message.get());
		}
		
		model.addAttribute("categories", list);
		return "admin/categories/sort";
	}
}
