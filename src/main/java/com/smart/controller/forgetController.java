package com.smart.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.smart.helper.Massage;

@Controller
public class forgetController {
	private int otp;

	@GetMapping("/forget_password")
	public String forget_password() {
		return "forget-email";
	}

	@PostMapping("/send_otp")
	public String send_otp(@RequestParam("email") String email) {
		System.out.println(email);
		Random rand = new Random(10000);
		this.otp = rand.nextInt(999999);
		System.out.println("otp " + otp);
		return "verify-otp";
	}

	@PostMapping("/verf_otp")
	public String verf_otp(@RequestParam("otp") String ottp, Model model, HttpSession session) {
		int ootp = Integer.parseInt(ottp);
		model.addAttribute("title", "Forget-Password");
		if (ootp == otp) {
			session.setAttribute("message", new Massage("Verify Sucessfully", "alert-success"));
		} else {
			session.setAttribute("message", new Massage("Invalid OTP", "alert-danger"));
		}
		return "forget-email";
	}
}
