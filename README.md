# Mobile-Billing App
<br>
This application can be used by customers for self checkout while shopping. The customer downloads the app from the Android Marketplace (App Store) and scans the items for self checkout without waiting in line to bill items in their cart. It's like having a personal checkout assistant.

<br>

### Functional Requirement

- Scan a barcode/QR code on the product
- Identify the product and price
- Add/Modify Payment Method
- Calculate the sub-totals, taxes and total price
- Accept payment and complete purchase

### User Requirements
- Mobile Device
- Android OS
- Application installed and compatible with user's android version

### App Features

#### Workflow

- Secure access with accont/Google authentication
- Track billing/invoice information, Order Summary
- QR Code Scanning
- Payment Method Storage
- Manage customer information

#### Payables

- Access your payment history
- Checkout quickly and easily
- Multiple payment options

### Receivables

- Manage products and customer
- Create invoice
- Keep track of payments received

### DFD Diagrams

- DFD level-0
![Image](DFD-0.jpg)

- DFD level-1
![Image1](DFD-1.jpg)



### App Modules and their responsibilities


#### 1. Login Page

- The main responsibility of this module is to provide authentication to App user.
- While running the App this module is considered as a home module.
- User can login using either with App mail or by sign in with google.

#### 2. Reset Password

- In this module when the user forgets his/her password he can go into this module.
- In this module it will ask users to provide their email id for the password recovery.
- After the password has been generated they will directed to the Main Activity module.

#### 3. Create Account

- When the user is a new one he/she can create his own account by opting this module.
- When the user clicks the Sign up button he is directed to this module.
- In this module he can create an account by providing his first name, last name, email and password.

#### 4. Main Activity

- After authentication of the user he/she will be directed to the main activity module.
- User can scan the shopping items by clicking the Scan QR code button or else he can manually select the products by clicking   the Products button.

#### 5. Shopping List

- In this module there will be a grid of shopping items where user will select them and add it to the cart.
- This module is responsible to display all the available items in the store and update whenever it is needed. 
- User can update and delete the items whenever he/she needed.

#### 6. Shopping Cart

- This module is responsible for displaying user’s selected shopping items in the shopping list module.
- Responsible for showing the quantity of the selected items.
- Responsible for calculating the total price of the shopping items.
- Responsible for sharing the receipt to your friends via mail, Whatsapp etc.

















