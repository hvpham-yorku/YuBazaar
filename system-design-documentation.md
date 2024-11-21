
# System Design Documentation

---

## Cover Page

**Project Title:** YU Bazaar System Design  
**Submission Date:** November 21, 2024  

---

## Table of Contents

1. [Introduction](#introduction)  
2. [System Overview](#system-overview)  
3. [Architecture Design](#architecture-design)  
4. [CRC Cards](#crc-cards)  

---

## Introduction

YU Bazaar is an exclusive marketplace designed for York University students, faculty, and staff. Access to the platform is restricted to users with valid York University email addresses (`@yorku.ca` or `@my.yorku.ca`). The platform facilitates a secure and convenient environment for buying and selling products, catering specifically to the York University community.

---

## System Overview

YU Bazaar is a marketplace for York University students, faculty, and staff, built using **Spring Boot**, **HTML**, **CSS**, **JavaScript**, **Thymeleaf**, **Java**, **JPA**, and **MySQL** (integrated via **PHP**).

### Workflow:

1. **User Authentication**: Only users with `@yorku.ca` or `@my.yorku.ca` emails can register, with email verification ensuring secure access.  
2. **Product Listings**: Sellers can create listings (title, description, price, photos), which are displayed dynamically using Thymeleaf.  
3. **Database**: **MySQL**, accessed via **PHP**, stores user data, product details, and inquiries.  
4. **Frontend**: Responsive UI built with **Thymeleaf**, **HTML**, **CSS**, and **JavaScript**.  
5. **Backend**: **Spring Boot** manages authentication, product operations, and communication between buyers and sellers.  

---

## Architecture Design

Maintained as a separate file: **MVC**  

---

## CRC Cards

CRC Cards are maintained as a separate file: [crc-cards.md](crc-cards.md)  

---
