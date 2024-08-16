package poly.edu.ka.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import poly.edu.ka.domain.Account;
import poly.edu.ka.model.AccountDto;
import poly.edu.ka.services.AccountService;

@Controller
@RequestMapping("site/account")
public class AccountSController {
	@Autowired
	AccountService accountService;
	
	@GetMapping("add")
	public String add(Model model) {
		model.addAttribute("account", new AccountDto());
		
		return "site/register";
	}
	
	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("account") AccountDto dto, BindingResult result) {
		if(result.hasErrors())
		{
			return new ModelAndView("admin/accounts/newOrEdit");

		}
		Account entity = new Account();
		
		BeanUtils.copyProperties(dto, entity);
	 
		accountService.save(entity);
		
		model.addAttribute("message", "Account is saved!");
		
		return new ModelAndView("forward:/site/account",model);
	}
	
	@GetMapping("edit/{username}")
	public ModelAndView edit(ModelMap model, @PathVariable("username")String username) {
		Optional<Account> opt = accountService.findById(username);
		AccountDto dto = new AccountDto();
		if(opt.isPresent())
		{
			Account entity = opt.get();
			BeanUtils.copyProperties(entity, dto);
			dto.setIsEdit(true);
			dto.setPassword("");
			model.addAttribute("account", dto);
			return new ModelAndView( "admin/accounts/newOrEdit",model);
		}
		model.addAttribute("message", "Account is not existed!");
		return new ModelAndView( "redirect:/site/account",model);

	}
}
