package poly.edu.ka.controller;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import poly.edu.ka.domain.Account;
import poly.edu.ka.domain.Product;
import poly.edu.ka.model.AccountDto;
import poly.edu.ka.model.AdminLoginDto;
import poly.edu.ka.repository.ProductRepository;
import poly.edu.ka.services.AccountService;
import poly.edu.ka.services.ProductService;
import poly.edu.ka.services.StorageService;

@Controller
@RequestMapping("site")
public class HomeController {
	@Autowired
	AccountService accountService;
	
	@Autowired
	ProductService productService;
	
	@Autowired
	StorageService storageService ;
	
	@Autowired
	ProductRepository productRepository;
	@Autowired
	private HttpSession session;

	@GetMapping("index")
	public String homePage(ModelMap model,  @RequestParam Optional<String> message, @PageableDefault(size = 8)Pageable pageable)
	{	
//			List<Product> list = productService.findAll();
//			model.addAttribute("products", list);
		Page<Product> pages = productRepository.findAll(pageable);
		if(message.isPresent())
		{
			model.addAttribute("message", message.get());
		}
		model.addAttribute("pages", pages);
		
		return "site/index";
	}

	@GetMapping("no-rights")
	public String notRights(ModelMap model) {
		model.addAttribute("account", new AdminLoginDto());
		
		return "/site/no-rights";
	}
	
	@GetMapping("products/images/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename)
	{
		Resource file = storageService.loadAsResource(filename);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION	, "attachment;filename=\"" +file.getFilename() +"\"").body(file);
	}
	
	@GetMapping("login")
	public String loginSite(ModelMap model) {
		model.addAttribute("account", new AdminLoginDto());
		
		return "/site/account/login";
	}
	
	@GetMapping("logout")
	public String logout(ModelMap model) {
	    // Invalidate the current session and clear the security context

	    return "redirect:/site/login";
	}
	
	@PostMapping("register")
	public String register(ModelMap model, @Valid @ModelAttribute("account") AccountDto dto, BindingResult result) {
		if(result.hasErrors())
		{
			return "site/account/register";

		}
		Account entity = new Account();
		
		BeanUtils.copyProperties(dto, entity);
	 
		accountService.save(entity);
		
		model.addAttribute("message", "Account is saved!");
		
		return "redirect:/site/index";
	}
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("account", new AccountDto());
		
		return "site/account/register";
	}
	@PostMapping("login")
	public ModelAndView login (ModelMap model,@Valid @ModelAttribute("account") AdminLoginDto dto, BindingResult result)

	{
		if(result.hasErrors())
		{
			return new ModelAndView("/site/login", model);
		}
		Account account = accountService.login(dto.getUsername(), dto.getPassword());
		if(account == null)
		{
			model.addAttribute("message", "Invalid username or password");
			return new ModelAndView("/site/login", model);
		}

		session.setAttribute("username", account.getUsername());
//		session.setAttribute(SessionAtrr.CURRENT_USER, account);

		Object ruri = session.getAttribute("redirect-uri");
		if(ruri != null)
		{
			session.removeAttribute("redirect-uri");
			return new ModelAndView("redirect:" +ruri);
		}
		
//		 if (account.getIsAdmin().equals("admin")) {
//		        return new ModelAndView("forward:/admin/categories"); // Đường dẫn admin
//		 }
//		     else {
//		        return new ModelAndView("redirect:/site/no-rights"); // Đường dẫn người dùng
//		    }
		return new ModelAndView("forward:/site/index", model);

	}
}
