package com.smart.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.smart.entity.Contact;
import com.smart.entity.User;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {
	@Query("from Contact c where c.myuser.id = :id")
	public Page<Contact> getContactsByUserId(@Param("id") int id, Pageable pageable);

	public List<Contact> findContactByNameIsContainingAndMyuser(String keyword, User user);

	public List<Contact> findContactByName(String name);

}
