package com.smart.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.helper.Massage;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository repository;
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	// Common Model Data
	@ModelAttribute
	public void common_data(Model model, Principal principal) {
		String username = principal.getName();
		System.out.println(username);
		User user = repository.getUserByUserName(username);
		System.out.println(user);
		model.addAttribute("user", user);
	}

	// DashBoard
	@RequestMapping("/index")
	public String dashboard_handler(Model model) {
		return "normal/user_dashboard";
	}

	// Add Contact Form
	@RequestMapping("/add_contact")
	public String add_contact(Model model) {
		model.addAttribute("title", "Add-Contact");
		model.addAttribute("contact", new Contact());
		return "normal/add_contact";
	}

	// Process Contact
	@PostMapping("/process-contact")
	public String process(@ModelAttribute Contact contact, Model model,
			@RequestParam("profileimage") MultipartFile file, HttpSession session) {
		if (file.isEmpty()) {
			// File is Empty
		} else {
			try {
				contact.setImageURL("contact.jpg");
				contact.setImageURL(file.getOriginalFilename());
				File f = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(f.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				User user = (User) model.getAttribute("user");
				contact.setUser(user);
				user.getContacts().add(contact);
				this.repository.save(user);
				System.out.println(contact);
				session.setAttribute("message", new Massage("Contact Added Successfully!!", "alert-success"));
				return "normal/add_contact";
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("message", new Massage("Something Went Wrong!!", "alert-danger"));
				return "normal/add_contact";
			}
		}
		session.setAttribute("message", new Massage("Something Went Wrong!!", "alert-danger"));
		return "normal/add_contact";
	}
	// View Contact List

	@RequestMapping("/viwe-contact/{page}")
	public String viwe_contact(@PathVariable("page") int page, Model model) {
		User user = (User) model.getAttribute("user");
		int id = user.getId();
		// Current Per Page
		// Number of Pages
		Pageable pageable = PageRequest.of(page, 6);
		Page<Contact> contact = this.contactRepository.getContactsByUserId(id, pageable);
		model.addAttribute("list", contact);
		model.addAttribute("currpage", page);
		model.addAttribute("total", contact.getTotalPages());
		return "normal/viwe-contact";
	}

	@RequestMapping("/{cId}/contact-detail")
	public String contact_detail_hadler(@PathVariable("cId") int id, Model model, HttpSession session) {
		System.out.println(id);
		Contact contact = this.contactRepository.findById(id).get();
		User user = (User) model.getAttribute("user");
		if (contact.getUser().getId() == user.getId())
			model.addAttribute("contact", contact);
		else {
			Massage massage = new Massage("User Not the part of your contact", "alert-primary");
			session.setAttribute("message", massage);
			return "normal/user_dashboard";
		}
		System.out.println(contact.getDescription());
		model.addAttribute("id", id);
		return "normal/contact_detail";
	}

	// delete
	@RequestMapping("/delete/{id}")
	public String delete_contact(@PathVariable("id") int id, Model model, HttpSession session) {
		try {
			Contact contact = this.contactRepository.getById(id);
			User user = (User) model.getAttribute("user");
			user.getContacts().remove(contact);
//			this.contactRepository.deleteById(id);
			this.repository.save(user);
			return "redirect:/user/viwe-contact/0";
		} catch (Exception e) {
			e.printStackTrace();
			Massage massage = new Massage("Something Went Wrong", "alert-primary");
			session.setAttribute("message", massage);
			return "normal/user_dashboard";
		}
	}

	// Update Handler
	@PostMapping("/update-contact/{cId}")
	public String update_handler(@PathVariable("cId") int id, Model model) {
		Contact contact = this.contactRepository.findById(id).get();
		model.addAttribute("contact", contact);
		return "normal/update_contact";
	}

	// Update Contact
	@PostMapping("/process-update/{cId}")
	public String process_update(@ModelAttribute Contact contact, Model model, @PathVariable("cId") int id,
			@RequestParam("profileimage") MultipartFile file, HttpSession session) {
		if (file.isEmpty()) {
			// File is Empty
		} else {
			try {
//				Contact contact1 = this.contactRepository.getById(id);
//				contact1.setUser(null);
//				this.contactRepository.deleteById(id);
				contact.setcId(id);
				contact.setImageURL("contact.jpg");
				contact.setImageURL(file.getOriginalFilename());
				File f = new ClassPathResource("static/images").getFile();
				Path path = Paths.get(f.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				User user = (User) model.getAttribute("user");
				contact.setUser(user);
				this.contactRepository.save(contact);
				System.out.println(contact);
				session.setAttribute("message", new Massage("Contact Added Successfully!!", "alert-success"));
				return "redirect:/user/" + contact.getcId() + "/contact-detail";
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("message", new Massage("Something Went Wrong!!", "alert-danger"));
				return "redirect:/user/update-contact/";
			}
		}
		session.setAttribute("message", new Massage("Something Went Wrong!!", "alert-danger"));
		return "redirect:/user/viwe-contact/0";
	}

	@RequestMapping("/profile")
	public String user_profile(Model model) {
		model.addAttribute("title", "Profile");
		return "normal/profile";
	}

	@GetMapping(value = "/setting")
	public String setting() {
		return "normal/setting";
	}

	@PostMapping("/change-password")
	public String changer_password(@RequestParam("old_password") String old_password,
			@RequestParam("new_password") String new_password, Principal principal, HttpSession session) {
		String user_name = principal.getName();
		User user = this.repository.getUserByUserName(user_name);
		System.out.println(user);
		if (this.bCryptPasswordEncoder.matches(old_password, user.getPassword())) {
			user.setPassword(this.bCryptPasswordEncoder.encode(new_password));
			this.repository.save(user);
			session.setAttribute("message", new Massage("Password Change Success Fully ", "alert-success"));
//			return "redirect:/logout";
		} else {
			session.setAttribute("message", new Massage("Something Went Wrong!!", "alert-danger"));
			return "redirect:/user/setting";
		}
		return "redirect:/user/index";
	}
}
