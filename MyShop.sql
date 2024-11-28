Create database MyShop
USE [MyShop]
GO
/****** Object:  Table [dbo].[cart]    Script Date: 11/4/2024 3:06:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cart](
	[cart_id] [bigint] NOT NULL,
	[product_id] [varchar](100) NOT NULL,
	[product_name] [varchar](255) NOT NULL,
	[product_img] [varchar](255) NOT NULL,
	[product_price] [float] NOT NULL,
	[total] [float] NOT NULL,
	[quantity] [int] NOT NULL,
 CONSTRAINT [PK_cart] PRIMARY KEY CLUSTERED 
(
	[cart_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[category]    Script Date: 11/4/2024 3:06:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[category](
	[category_id] [int] IDENTITY(1,1) NOT NULL,
	[category_name] [varchar](255) NOT NULL,
 CONSTRAINT [PK_category] PRIMARY KEY CLUSTERED 
(
	[category_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order]    Script Date: 11/4/2024 3:06:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order](
	[order_id] [bigint] IDENTITY(1,1) NOT NULL,
	[user_id] [bigint] NOT NULL,
	[total] [money] NOT NULL,
	[payment] [varchar](250) NOT NULL,
	[date] [date] NOT NULL,
 CONSTRAINT [PK_order] PRIMARY KEY CLUSTERED 
(
	[order_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_detail]    Script Date: 11/4/2024 3:06:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_detail](
	[detail_id] [bigint] IDENTITY(1,1) NOT NULL,
	[order_id] [bigint] NOT NULL,
	[product_id] [varchar](100) NOT NULL,
	[quantity] [int] NOT NULL,
	[size] [nchar](10) NOT NULL,
	[color] [nvarchar](150) NOT NULL,
	[price] [money] NOT NULL,
 CONSTRAINT [PK_order_detail] PRIMARY KEY CLUSTERED 
(
	[detail_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product]    Script Date: 11/4/2024 3:06:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product](
	[product_id] [varchar](100) NOT NULL,
	[category_id] [int] NOT NULL,
	[product_name] [nvarchar](max) NOT NULL,
	[product_price] [money] NOT NULL,
	[product_describe] [nvarchar](max) NOT NULL,
	[quantity] [int] NOT NULL,
	[img] [nvarchar](50) NOT NULL,
 CONSTRAINT [PK_product] PRIMARY KEY CLUSTERED 
(
	[product_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_color]    Script Date: 11/4/2024 3:06:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_color](
	[product_id] [varchar](100) NOT NULL,
	[color] [nvarchar](50) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_size]    Script Date: 11/4/2024 3:06:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_size](
	[product_id] [varchar](100) NOT NULL,
	[size] [nvarchar](50) NOT NULL
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 11/4/2024 3:06:47 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[user_id] [bigint] IDENTITY(1,1) NOT NULL,
	[user_name] [nvarchar](200) NULL,
	[user_email] [varchar](255) NOT NULL,
	[user_pass] [nvarchar](255) NOT NULL,
	[isAdmin] [nvarchar](50) NULL,
	[phone] [nvarchar](15) NULL,
	[address] [nvarchar](50) NULL,
 CONSTRAINT [PK_users] PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[cart]  WITH CHECK ADD  CONSTRAINT [FK_cart_product] FOREIGN KEY([product_id])
REFERENCES [dbo].[product] ([product_id])
GO
ALTER TABLE [dbo].[cart] CHECK CONSTRAINT [FK_cart_product]
GO
ALTER TABLE [dbo].[order]  WITH CHECK ADD  CONSTRAINT [FK_order_users] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[order] CHECK CONSTRAINT [FK_order_users]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FK_order_detail_order] FOREIGN KEY([order_id])
REFERENCES [dbo].[order] ([order_id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FK_order_detail_order]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FK_order_detail_product] FOREIGN KEY([product_id])
REFERENCES [dbo].[product] ([product_id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FK_order_detail_product]
GO
ALTER TABLE [dbo].[product_color]  WITH CHECK ADD  CONSTRAINT [FK_product_color_product] FOREIGN KEY([product_id])
REFERENCES [dbo].[product] ([product_id])
GO
ALTER TABLE [dbo].[product_color] CHECK CONSTRAINT [FK_product_color_product]
GO
ALTER TABLE [dbo].[product_size]  WITH CHECK ADD  CONSTRAINT [FK_product_size_product] FOREIGN KEY([product_id])
REFERENCES [dbo].[product] ([product_id])
GO
ALTER TABLE [dbo].[product_size] CHECK CONSTRAINT [FK_product_size_product]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FK_product_category] FOREIGN KEY([category_id])
REFERENCES [dbo].[category] ([category_id])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FK_product_category]
GO

INSERT INTO [dbo].[users] (user_name, user_email, user_pass, isAdmin, phone, address)
VALUES 
('Admin', 'admin@gmail.com', 'admin', 'true', 'Test', 'Test'),
(N'Mạc Viết Thông', 'nhokvipprozzz@gmail.com', '123456', 'false', '0987654321', '66 Ngai Chuyen, Ninh Kieu, Can Tho'),
(N'Nguyễn Ngọc Minh', 'ngocminhcute@gmail.com', '123456', 'false', '0976543210', '72/78 Hem 10A, Thu Duc');

INSERT INTO [dbo].[category] (category_name)
VALUES ('ORIGINALS'),
       ('FOOTBALL'),
	   ('BASKETBALL'),
	   ('RUNNING'),
	   ('TRAINING'),
	   ('SNEAKERS'),
	   ('SLIDES & FLIP FLOPS');
	   INSERT INTO [dbo].[product] (product_id, category_id, product_name, product_price, product_describe, quantity, img)
VALUES 
('P001', 1, N'Giầy vải Thượng Đình KK14-1', 100.000, N'Giầy vải Thượng Đình Original', 20, 'images/P001.jpg');

INSERT INTO [dbo].[product] (product_id, category_id, product_name, product_price, product_describe, quantity, img)
VALUES 
('P002', 1, N'Gazelle Indoor x Hello Kitty Anniversary Shoes', 2900.000, N'Giầy Adidas Original ', 30, 'images/P002.jpg'),
('P003', 2, N'Giầy vải Thượng Đình BD05', 300.000, N'Giầy vải Thượng Đình Football', 40, 'images/P003.jpg'),
('P004', 2, N'Predator League Firm Ground Boots', 2400.000, N'Giầy Adidas Football', 50, 'images/P004.jpg'),
('P005', 3, N'Giày bóng rổ đế thấp Fast 500', 1195.000 , N'Giầy TARMAK Basketball', 40, 'images/P005.jpg'),
('P006', 3, N'D.O.N. Issue #6 Spida Basketball Shoes', 3200.000, N'Giầy Adidas Football', 40, 'images/P006.jpg'),
('P007', 4, N'Nike Revolution 7 EasyOn', 1789.000, N'Giầy Nike Running', 30, 'images/P007.jpg'),
('P008', 4, N'Nike Invincible 3 By You', 6179.000, N'Giầy Nike Running', 40, 'images/P008.jpg'),
('P009', 5, N'Nike Legend Essential 3 Next Nature', 1909.000, N'Giầy Nike Trainning', 40, 'images/P009.jpg');


INSERT INTO [dbo].[product] (product_id, category_id, product_name, product_price, product_describe, quantity, img)
VALUES 
('P010', 5, N'Nike Free Metcon 6', 3519.000, N'Giầy Nike Trainning', 50, 'images/P010.jpg'),
('P011', 6, N'Air Jordan 1 Low', 3239.000, N'Giầy Nike Sneaker', 60, 'images/P011.jpg'),
('P012', 6, N'Air Jordan I High G', 5279.000, N'Giầy Nike Sneaker', 50, 'images/P012.jpg'),
('P013', 7, N'Adilette Korn Slides', 950.000, N'Dép Adidas', 40, 'images/P013.jpg'),
('P014', 7, N'Jordan Hydro XI', 1909.000, N'Dép Nike', 60, 'images/P014.jpg');
INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P001', 'Black'),
('P001', 'White'),
('P001', 'Green'),
('P001', 'Blue'),
('P001', 'Purple'),
('P001', 'Red'),
('P001', 'Yellow'),
('P001', 'Orange');

INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P002', 'Black'),
('P002', 'White'),
('P002', 'Green'),
('P002', 'Blue'),
('P002', 'Purple'),
('P002', 'Red'),
('P002', 'Yellow'),
('P002', 'Orange');

INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P003', 'Black'),
('P003', 'White'),
('P003', 'Green'),
('P003', 'Blue'),
('P003', 'Purple'),
('P003', 'Red'),
('P003', 'Yellow'),
('P003', 'Orange');

INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P004', 'Black'),
('P004', 'White'),
('P004', 'Green'),
('P004', 'Blue'),
('P004', 'Purple'),
('P004', 'Red'),
('P004', 'Yellow'),
('P004', 'Orange');

INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P005', 'Black'),
('P005', 'White'),
('P005', 'Green'),
('P005', 'Blue'),
('P005', 'Purple'),
('P005', 'Red'),
('P005', 'Yellow'),
('P005', 'Orange');

INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P006', 'Black'),
('P006', 'White'),
('P006', 'Green'),
('P006', 'Blue'),
('P006', 'Purple'),
('P006', 'Red'),
('P006', 'Yellow'),
('P006', 'Orange');

INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P007', 'Black'),
('P007', 'White'),
('P007', 'Green'),
('P007', 'Blue'),
('P007', 'Purple'),
('P007', 'Red'),
('P007', 'Yellow'),
('P007', 'Orange');

INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P008', 'Black'),
('P008', 'White'),
('P008', 'Green'),
('P008', 'Blue'),
('P008', 'Purple'),
('P008', 'Red'),
('P008', 'Yellow'),
('P008', 'Orange');

INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P009', 'Black'),
('P009', 'White'),
('P009', 'Green'),
('P009', 'Blue'),
('P009', 'Purple'),
('P009', 'Red'),
('P009', 'Yellow'),
('P009', 'Orange');

INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P010', 'Black'),
('P010', 'White'),
('P010', 'Green'),
('P010', 'Blue'),
('P010', 'Purple'),
('P010', 'Red'),
('P010', 'Yellow'),
('P010', 'Orange');

INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P011', 'Black'),
('P011', 'White'),
('P011', 'Green'),
('P011', 'Blue'),
('P011', 'Purple'),
('P011', 'Red'),
('P011', 'Yellow'),
('P011', 'Orange');

INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P012', 'Black'),
('P012', 'White'),
('P012', 'Green'),
('P012', 'Blue'),
('P012', 'Purple'),
('P012', 'Red'),
('P012', 'Yellow'),
('P012', 'Orange');

INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P013', 'Black'),
('P013', 'White'),
('P013', 'Green'),
('P013', 'Blue'),
('P013', 'Purple'),
('P013', 'Red'),
('P013', 'Yellow'),
('P013', 'Orange');

INSERT INTO [dbo].[product_color] (product_id, color)
VALUES 
('P014', 'Black'),
('P014', 'White'),
('P014', 'Green'),
('P014', 'Blue'),
('P014', 'Purple'),
('P014', 'Red'),
('P014', 'Yellow'),
('P014', 'Orange');
INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P001', '36'),
('P001', '42'),
('P001', '45');

INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P002', '36'),
('P002', '42'),
('P002', '45');

INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P003', '36'),
('P003', '42'),
('P003', '45');

INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P004', '36'),
('P004', '42'),
('P004', '45');

INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P005', '36'),
('P005', '42'),
('P005', '45');

INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P006', '36'),
('P006', '42'),
('P006', '45');

INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P007', '36'),
('P007', '42'),
('P007', '45');

INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P008', '36'),
('P008', '42'),
('P008', '45');

INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P009', '36'),
('P009', '42'),
('P009', '45');

INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P010', '36'),
('P010', '42'),
('P010', '45');

INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P011', '36'),
('P011', '42'),
('P011', '45');

INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P012', '36'),
('P012', '42'),
('P012', '45');

INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P013', '36'),
('P013', '42'),
('P013', '45');

INSERT INTO [dbo].[product_size] (product_id, size)
VALUES 
('P014', '36'),
('P014', '42'),
('P014', '45');

