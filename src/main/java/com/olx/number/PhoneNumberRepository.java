package com.olx.number;

import org.springframework.data.jpa.repository.JpaRepository;

interface PhoneNumberRepository extends JpaRepository<PhoneNumber, String> {
}
