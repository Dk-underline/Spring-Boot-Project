package com.smart.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.smart.dao.ContactRepository;
import com.smart.dao.UserRepository;
import com.smart.entity.Contact;
import com.smart.entity.User;
import com.smart.helper.Data;

@RestController
public class SearchController {
	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private UserRepository userRepository;

	@GetMapping(value = "/search/{query}")
	public ResponseEntity<?> search(@PathVariable("query") String name, Principal principal) {
		System.out.println(name + " " + "search query");
		User myuser = this.userRepository.getUserByUserName(principal.getName());
		System.out.println(myuser.toString());
		List<Contact> contacts = this.contactRepository.findContactByNameIsContainingAndMyuser(name, myuser);
		return ResponseEntity.ok(contacts);
	}

	@GetMapping(value = "/searchhh/{query}")
	public List<Data> searchh(@PathVariable("query") String name, Principal principal) {
		System.out.println(name + " " + "search query");
		User myuser = this.userRepository.getUserByUserName(principal.getName());
		System.out.println(myuser.toString());
		List<Contact> contacts = this.contactRepository.findContactByNameIsContainingAndMyuser(name, myuser);
		System.out.println("contacts begin");
		List<Data> ls = new ArrayList<>();
		for (Contact contact : contacts) {
			ls.add(new Data(contact.getName(), contact.getcId()));
		}
		System.out.println("contacts end");
//		/return contacts.toString();
		return ls;
	}

}
