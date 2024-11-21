
# CRC Cards

---

## TemplateController

| **Class Name**      | TemplateController                                      |
|---------------------|--------------------------------------------------------|
| **Responsibilities**| - Handle user authentication (login, logout).          |
|                     | - Render login, register, and forgot password pages.   |
|                     | - Process user registration and password reset.        |
| **Collaborators**    | - [User](#user)                                        |
|                     | - [UserRepository](#userrepository)                    |
|                     | - `login_page.html`, `register_page.html`, `forgot_password.html` |

---

## ItemController

| **Class Name**      | ItemController                                          |
|---------------------|--------------------------------------------------------|
| **Responsibilities**| - Handle HTTP requests for item-related operations.     |
|                     | - Render the home page with items.                     |
|                     | - Add new items to the system.                         |
|                     | - Delete items from the system.                        |
| **Collaborators**    | - [Item](#item)                                        |
|                     | - [ItemRepository](#itemrepository)                   |
|                     | - `home_page.html`                                     |

---

## Item

| **Class Name**      | Item                                                    |
|---------------------|--------------------------------------------------------|
| **Responsibilities**| - Represent an item entity in the system.               |
|                     | - Store information about the item (e.g., title, price, wear, etc.). |
| **Collaborators**    | - [ItemRepository](#itemrepository)                    |
|                     | - [ItemController](#itemcontroller)                   |

---

## User

| **Class Name**      | User                                                    |
|---------------------|--------------------------------------------------------|
| **Responsibilities**| - Represent a user entity in the system.                |
|                     | - Store user information (e.g., name, email, password).|
|                     | - Facilitate user authentication and authorization.    |
| **Collaborators**    | - [UserRepository](#userrepository)                    |
|                     | - [TemplateController](#templatecontroller)           |

---

## ItemRepository

| **Class Name**      | ItemRepository                                          |
|---------------------|--------------------------------------------------------|
| **Responsibilities**| - Interact with the database for item-related queries.  |
|                     | - Provide methods to save, retrieve, and delete items. |
| **Collaborators**    | - [Item](#item)                                        |
|                     | - [ItemController](#itemcontroller)                   |

---

## UserRepository

| **Class Name**      | UserRepository                                          |
|---------------------|--------------------------------------------------------|
| **Responsibilities**| - Interact with the database for user-related queries.  |
|                     | - Provide methods to save, retrieve, and manage users. |
| **Collaborators**    | - [User](#user)                                        |
|                     | - [TemplateController](#templatecontroller)           |

---

## EmailSender

| **Class Name**      | EmailSender                                             |
|---------------------|--------------------------------------------------------|
| **Responsibilities**| - Send emails for user registration and password recovery. |
|                     | - Use templates to format emails.                      |
| **Collaborators**    | - [TemplateController](#templatecontroller)           |
|                     | - [EmailTemplate](#emailtemplate)                     |

---

## EmailTemplate

| **Class Name**      | EmailTemplate                                           |
|---------------------|--------------------------------------------------------|
| **Responsibilities**| - Provide predefined templates for emails.              |
|                     | - Generate email subjects and bodies dynamically.      |
| **Collaborators**    | - [EmailSender](#emailsender)                          |
|                     | - [TemplateController](#templatecontroller)           |

---
