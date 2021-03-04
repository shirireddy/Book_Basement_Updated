CREATE DATABASE book_basement1;

CREATE TABLE books
(
	 book_id 	INT PRIMARY KEY AUTO_INCREMENT,
	 title		VARCHAR(50)  UNIQUE ,
	 authorFirstName 		VARCHAR(50) NOT NULL ,
	 authorLastName	VARCHAR(50),
	 genre		VARCHAR(50) NOT NULL ,
	 price 		INT
)

INSERT INTO books(title,authorFirstName,authorLastName,genre,price)
				VALUES('MR.NOBODY','CATHERINE','STEADMAN','MYSTERY',350);
INSERT INTO books(title,authorFirstName,authorLastName,genre,price)
				VALUES('ROXANE GAY: WRITING INTO THE WOUND','ROXANE','GAY','PERSONAL MEMORIES',450);
INSERT INTO books(title,authorFirstName,authorLastName,genre,price)
				VALUES('THE DAYS OF WINE AND COVID','ELIZABETH','BERG','REALITY POETRY',528);
INSERT INTO books(title,authorFirstName,authorLastName,genre,priceś)
				VALUES('THE ONLY GIRL LIVING ON EARTH','CHARLED','YU','FICTION',340);
INSERT INTO books(title,authorFirstName,authorLastName,genre,price)
				VALUES ('THE VAMPIRE DIARIES','L.J.SMITH','TURNER','SUPERNATURAL',780);
INSERT INTO books(title,authorFirstName,authorLastName,genre,price)
				VALUES('A SONG OF ICE AND FIRE - A GAME OF THRONES','GEORGE','R.R.MARTIN','FANTASY',1200);
INSERT INTO books(title,authorFirstName,authorLastName,genre,price)
				VALUES('WINGS OF FIRE','Dr.A.P.J.ABDUL','KALAM','BIOGRAPHY',2200);
INSERT INTO books(title,authorFirstName,authorLastName,genre,price)
				VALUES('WILL YOU CRY WHEN YOU DIE','ROBIN','SHARMA','PHISIOLOGY',500);
INSERT INTO books(title,authorFirstName,authorLastName,genre,price)
				VALUES('A GIRL TO REMEMBER','AJAY','PANDEY','FICTION',590);
INSERT INTO books(title,authorFirstName,authorLastName,genre,price)
				VALUES('THE POWER OF YOUR SUBCONSCIOUS MIND','JOSEPH','MURPHY','INSPIRATIONAL',780);


 CREATE TABLE user(
user_id INT PRIMARY KEY AUTO_INCREMENT,book_basement1book_basement1user
FirstName VARCHAR(25) NOT NULL,
LastName VARCHAR(25) NOT NULL,
PhoneNumber VARCHAR(10),
gender VARCHAR(25) NOT NULL,
password VARCHAR(25) NOT NULL,
mail_id VARCHAR(50) NOT NULL  CHECK(mail_id REGEXP '^[A-Za-z0-9._%\-+
!#$&/=?^|~]+@[A-Za-z0-9.-]+[.][A-Za-z]+$'));     book_basement1

 

CREATE TABLE user_details(
            userDetails_id     INT PRIMARY KEY AUTO_INCREMENT,
         userid             INT NOT NULL,
         house_number        VARCHAR(25)  NOT NULL,
           city                VARCHAR(25)  NOT NULL,     
           state               VARCHAR(25) NOT NULL,  
         Pincode             VARCHAR(10) NOT NULL,
       
       
          CONSTRAINT     fk_user_details  FOREIGN KEY (userid)
          REFERENCES user(user_id)
        ON DELETE CASCADE
       ON UPDATE RESTRICT
        );      book_basement1
        
 CREATE TABLE userList(
     user_id int PRIMARY KEY AUTO_INCREMENT,
     userFirstName VARCHAR(25),
     userLastName VARCHAR(25) );  book_basement1
      
CREATE TABLE admin(
         adminid             INT  PRIMARY KEY AUTO_INCREMENT,
         FirstName              VARCHAR(25) NOT NULL,
         LastName              VARCHAR(25) NOT NULL,    
          PASSWORD           VARCHAR(25) NOT NULL 
        );           
        
 INSERT INTO admin(FirstName,LastName,PASSWORD) VALUES('Default','Admin','Password'); 
 
 CREATE TABLE orders(
  order_id INT PRIMARY KEY AUTO_INCREMENT ,book_basement1
  customer_id INT NOT NULL,
  book_reference_code INT NOT NULL,
  orderDate VARCHAR(30) NOT NULL,
  CONSTRAINT     fk1_orders  FOREIGN KEY (book_reference_code)
          REFERENCES books (book_id    )
          ON DELETE cascade
          ON UPDATE cascade
            );    
            
  CREATE TABLE payments(
payment_id INT PRIMARY KEY AUTO_INCREMENT,
customerNumber INT NOT NULL,
payments INT NOT NULL,
CONSTRAINT fk_payments FOREIGN KEY(customerNumber)
            REFERENCES user (user_id)
            ON DELETE cascade
            );             book_basement1
            
  CREATE TABLE wallet(
walletId INT PRIMARY KEY,
PASSWORD VARCHAR(25) NOT NULL,
balance INT(11),
CONSTRAINT fk_wallet FOREIGN KEY(walletId)
                REFERENCES user(user_id)
                on delete CASCADE ); 

	
