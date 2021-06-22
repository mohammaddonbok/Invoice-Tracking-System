# Invoice-Tracking-System
## Project Introduction
Invoices Tracker is a system used for tracking invoices.
Imagine that there is a Manager who has control on everything  ,
sales representative who only control his own invoices and 
the Auditor who only view all the invoices for all sales representative for Audit.
All users can interact with the system insert invoices search and etc..

## Users Roles

-SUPER-ADMIN
    - it represent the company manager who can perform all actions on all invoices in the system.
    - Can log-in , log-out , Can create accounts for his employees and determine there roles.
    - Can create , edit , delete , view and search on all invoices.
    - Can add , edit items on invoices.
    - Can deactivate and activate users also can change invoices owner so if the super-admin deactivate user he can change the owner of specific invoice to active user. 
-SUPPORT-USER
  -Can log-in , log-out
  -it represent the company sales representative who can perform actions only on his own invoices
  -Can add , delete , edit and view and search his own invoices only and he don’t have access on others invoices
-Auditor
  - Can log-in , log-out.
  -Auditor role represent the company finance audit who can view all invoices.
  -The role of auditor can only view the invoices and search for invoices for all users.

## Data Base Model:


