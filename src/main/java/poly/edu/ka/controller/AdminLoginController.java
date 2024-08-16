package poly.edu.ka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import poly.edu.ka.constants.SessionAtrr;
import poly.edu.ka.domain.Account;
import poly.edu.ka.model.AdminLoginDto;
import poly.edu.ka.services.AccountService;

@Controller
public class AdminLoginController {
	@Autowired
	private AccountService accountService;
	
	@Autowired
	private HttpSession session;
	
	@GetMapping("alogin")
	public String login(ModelMap model) {
		model.addAttribute("account", new AdminLoginDto());
		return "/admin/accounts/login";
	}
	@PostMapping("alogin")
	public ModelAndView login (ModelMap model,@Valid @ModelAttribute("account") AdminLoginDto dto, BindingResult result)

	{
		if(result.hasErrors())
		{
			return new ModelAndView("/admin/accounts/login", model);
		}
		Account account = accountService.login(dto.getUsername(), dto.getPassword());
		if(account == null)
		{
			model.addAttribute("message", "Invalid username or password");
			return new ModelAndView("/admin/accounts/login", model);
		}

		session.setAttribute("username", account.getUsername());
//		session.setAttribute(SessionAtrr.CURRENT_USER, account);

		Object ruri = session.getAttribute("redirect-uri");
		if(ruri != null)
		{
			session.removeAttribute("redirect-uri");
			return new ModelAndView("redirect:" +ruri);
		}
		
		 if (account.getIsAdmin().equals("admin")) {
		        return new ModelAndView("forward:/admin/products"); // Đường dẫn admin
		 }else {
		        return new ModelAndView("redirect:/site/no-rights"); // Đường dẫn người dùng
		    }
//		return new ModelAndView("forward:/admin/categories", model);

	}
}
