USE [CarRental]
GO
ALTER TABLE [dbo].[Feedback] DROP CONSTRAINT [FK_Feedback_BillDetail]
GO
ALTER TABLE [dbo].[BillDetail] DROP CONSTRAINT [FK_BillDetail_Car]
GO
ALTER TABLE [dbo].[BillDetail] DROP CONSTRAINT [FK_BillDetail_Bill]
GO
ALTER TABLE [dbo].[Bill] DROP CONSTRAINT [FK_Bill_User]
GO
ALTER TABLE [dbo].[Bill] DROP CONSTRAINT [FK_Bill_Discount]
GO
/****** Object:  Table [dbo].[User]    Script Date: 3/6/2021 9:59:12 PM ******/
DROP TABLE [dbo].[User]
GO
/****** Object:  Table [dbo].[Feedback]    Script Date: 3/6/2021 9:59:12 PM ******/
DROP TABLE [dbo].[Feedback]
GO
/****** Object:  Table [dbo].[Discount]    Script Date: 3/6/2021 9:59:12 PM ******/
DROP TABLE [dbo].[Discount]
GO
/****** Object:  Table [dbo].[Car]    Script Date: 3/6/2021 9:59:12 PM ******/
DROP TABLE [dbo].[Car]
GO
/****** Object:  Table [dbo].[BillDetail]    Script Date: 3/6/2021 9:59:12 PM ******/
DROP TABLE [dbo].[BillDetail]
GO
/****** Object:  Table [dbo].[Bill]    Script Date: 3/6/2021 9:59:12 PM ******/
DROP TABLE [dbo].[Bill]
GO
USE [master]
GO
/****** Object:  Database [CarRental]    Script Date: 3/6/2021 9:59:12 PM ******/
DROP DATABASE [CarRental]
GO
/****** Object:  Database [CarRental]    Script Date: 3/6/2021 9:59:12 PM ******/
CREATE DATABASE [CarRental]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'CarRental', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\CarRental.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'CarRental_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL14.MSSQLSERVER\MSSQL\DATA\CarRental_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
GO
ALTER DATABASE [CarRental] SET COMPATIBILITY_LEVEL = 140
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [CarRental].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [CarRental] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [CarRental] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [CarRental] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [CarRental] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [CarRental] SET ARITHABORT OFF 
GO
ALTER DATABASE [CarRental] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [CarRental] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [CarRental] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [CarRental] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [CarRental] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [CarRental] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [CarRental] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [CarRental] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [CarRental] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [CarRental] SET  DISABLE_BROKER 
GO
ALTER DATABASE [CarRental] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [CarRental] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [CarRental] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [CarRental] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [CarRental] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [CarRental] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [CarRental] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [CarRental] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [CarRental] SET  MULTI_USER 
GO
ALTER DATABASE [CarRental] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [CarRental] SET DB_CHAINING OFF 
GO
ALTER DATABASE [CarRental] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [CarRental] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [CarRental] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [CarRental] SET QUERY_STORE = OFF
GO
USE [CarRental]
GO
/****** Object:  Table [dbo].[Bill]    Script Date: 3/6/2021 9:59:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bill](
	[billId] [varchar](50) NOT NULL,
	[username] [varchar](100) NULL,
	[createDate] [date] NULL,
	[status] [varchar](50) NULL,
	[discountCode] [varchar](50) NULL,
	[totalPrice] [float] NULL,
	[rentalDate] [datetime] NULL,
	[returnDate] [datetime] NULL,
 CONSTRAINT [PK_Bill] PRIMARY KEY CLUSTERED 
(
	[billId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BillDetail]    Script Date: 3/6/2021 9:59:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BillDetail](
	[billDetailId] [varchar](50) NOT NULL,
	[billId] [varchar](50) NULL,
	[carId] [varchar](50) NULL,
	[totalCar] [int] NULL,
	[totalPrice] [float] NULL,
	[rentalDate] [datetime] NULL,
	[returnDate] [datetime] NULL,
 CONSTRAINT [PK_BillDetails] PRIMARY KEY CLUSTERED 
(
	[billDetailId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Car]    Script Date: 3/6/2021 9:59:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Car](
	[carId] [varchar](50) NOT NULL,
	[color] [varchar](100) NULL,
	[year] [int] NULL,
	[category] [varchar](50) NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[createDate] [datetime] NULL,
	[carName] [varchar](50) NULL,
	[status] [bit] NULL,
 CONSTRAINT [PK_Car] PRIMARY KEY CLUSTERED 
(
	[carId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Discount]    Script Date: 3/6/2021 9:59:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Discount](
	[discountCode] [varchar](50) NOT NULL,
	[saleOff] [float] NULL,
	[expirationDate] [datetime] NULL,
 CONSTRAINT [PK_Discount] PRIMARY KEY CLUSTERED 
(
	[discountCode] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Feedback]    Script Date: 3/6/2021 9:59:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Feedback](
	[feedbackId] [varchar](50) NOT NULL,
	[billDetailId] [varchar](50) NULL,
	[content] [varchar](50) NULL,
	[rating] [varchar](50) NULL,
 CONSTRAINT [PK_Feedback] PRIMARY KEY CLUSTERED 
(
	[feedbackId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 3/6/2021 9:59:13 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[username] [varchar](100) NOT NULL,
	[fullname] [varchar](50) NULL,
	[role] [varchar](50) NULL,
	[password] [varchar](50) NULL,
	[createDate] [date] NULL,
 CONSTRAINT [PK_User] PRIMARY KEY CLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]
GO
INSERT [dbo].[Bill] ([billId], [username], [createDate], [status], [discountCode], [totalPrice], [rentalDate], [returnDate]) VALUES (N'7d7439ab-bd69-453a-bb1b-65282b65a4ba', N'acma8x01br@gmail.com', CAST(N'2021-03-06' AS Date), N'Activate', NULL, 27, CAST(N'2021-03-08T00:00:00.000' AS DateTime), CAST(N'2021-03-11T00:00:00.000' AS DateTime))
INSERT [dbo].[Bill] ([billId], [username], [createDate], [status], [discountCode], [totalPrice], [rentalDate], [returnDate]) VALUES (N'bf9977c1-8339-495a-83ac-59c709b7cf81', N'acma8x01br@gmail.com', CAST(N'2021-03-06' AS Date), N'InActivate', NULL, 10, CAST(N'2021-03-07T00:00:00.000' AS DateTime), CAST(N'2021-03-09T00:00:00.000' AS DateTime))
INSERT [dbo].[BillDetail] ([billDetailId], [billId], [carId], [totalCar], [totalPrice], [rentalDate], [returnDate]) VALUES (N'2abde9cf-dab5-4960-b693-c8352014e1d4', N'7d7439ab-bd69-453a-bb1b-65282b65a4ba', N'D', 1, 12, CAST(N'2021-03-08T00:00:00.000' AS DateTime), CAST(N'2021-03-11T00:00:00.000' AS DateTime))
INSERT [dbo].[BillDetail] ([billDetailId], [billId], [carId], [totalCar], [totalPrice], [rentalDate], [returnDate]) VALUES (N'38c2b513-bfd5-4017-825d-fa691350e250', N'bf9977c1-8339-495a-83ac-59c709b7cf81', N'E', 1, 10, CAST(N'2021-03-07T00:00:00.000' AS DateTime), CAST(N'2021-03-09T00:00:00.000' AS DateTime))
INSERT [dbo].[Car] ([carId], [color], [year], [category], [price], [quantity], [createDate], [carName], [status]) VALUES (N'A', N'blue', 2000, N'A', 1, 10, CAST(N'2021-02-28T00:00:11.000' AS DateTime), N'A', 1)
INSERT [dbo].[Car] ([carId], [color], [year], [category], [price], [quantity], [createDate], [carName], [status]) VALUES (N'B', N'pink', 2000, N'A', 2, 11, CAST(N'2021-02-28T00:00:12.000' AS DateTime), N'A', 1)
INSERT [dbo].[Car] ([carId], [color], [year], [category], [price], [quantity], [createDate], [carName], [status]) VALUES (N'C', N'red', 2001, N'B', 3, 12, CAST(N'2021-02-28T00:00:13.000' AS DateTime), N'V', 1)
INSERT [dbo].[Car] ([carId], [color], [year], [category], [price], [quantity], [createDate], [carName], [status]) VALUES (N'D', N'yellow', 2002, N'C', 4, 13, CAST(N'2021-02-28T00:00:14.000' AS DateTime), N'S', 1)
INSERT [dbo].[Car] ([carId], [color], [year], [category], [price], [quantity], [createDate], [carName], [status]) VALUES (N'E', N'grey', 2002, N'D', 5, 14, CAST(N'2021-02-28T00:00:15.000' AS DateTime), N'R', 1)
INSERT [dbo].[Car] ([carId], [color], [year], [category], [price], [quantity], [createDate], [carName], [status]) VALUES (N'F', N'123', 1999, N'B', 12, 12, CAST(N'2021-03-04T20:41:48.697' AS DateTime), N'Huy', 1)
INSERT [dbo].[Car] ([carId], [color], [year], [category], [price], [quantity], [createDate], [carName], [status]) VALUES (N'G', N'123', 1233, N'B', 15, 12, CAST(N'2021-03-04T20:44:54.490' AS DateTime), N'Ice cream', 1)
INSERT [dbo].[Discount] ([discountCode], [saleOff], [expirationDate]) VALUES (N'A', 0.1, CAST(N'2021-03-29T00:00:00.000' AS DateTime))
INSERT [dbo].[Feedback] ([feedbackId], [billDetailId], [content], [rating]) VALUES (N'ee58ea8b-9ee6-464f-bcf0-04e875d0ce03', N'2abde9cf-dab5-4960-b693-c8352014e1d4', N'aaaa', N'1')
INSERT [dbo].[Feedback] ([feedbackId], [billDetailId], [content], [rating]) VALUES (N'f34b7318-81f1-4b5e-8cab-278bbf4ab4fb', N'2abde9cf-dab5-4960-b693-c8352014e1d4', N'aaaa', N'1')
INSERT [dbo].[User] ([username], [fullname], [role], [password], [createDate]) VALUES (N'acma8x01br@gmail.com', N'Huy', N'0', N'123', CAST(N'2021-03-03' AS Date))
INSERT [dbo].[User] ([username], [fullname], [role], [password], [createDate]) VALUES (N'huylngse140029@fpt.edu.vn', N'Huy', N'1', N'123', CAST(N'2021-02-27' AS Date))
ALTER TABLE [dbo].[Bill]  WITH CHECK ADD  CONSTRAINT [FK_Bill_Discount] FOREIGN KEY([discountCode])
REFERENCES [dbo].[Discount] ([discountCode])
GO
ALTER TABLE [dbo].[Bill] CHECK CONSTRAINT [FK_Bill_Discount]
GO
ALTER TABLE [dbo].[Bill]  WITH CHECK ADD  CONSTRAINT [FK_Bill_User] FOREIGN KEY([username])
REFERENCES [dbo].[User] ([username])
GO
ALTER TABLE [dbo].[Bill] CHECK CONSTRAINT [FK_Bill_User]
GO
ALTER TABLE [dbo].[BillDetail]  WITH CHECK ADD  CONSTRAINT [FK_BillDetail_Bill] FOREIGN KEY([billId])
REFERENCES [dbo].[Bill] ([billId])
GO
ALTER TABLE [dbo].[BillDetail] CHECK CONSTRAINT [FK_BillDetail_Bill]
GO
ALTER TABLE [dbo].[BillDetail]  WITH CHECK ADD  CONSTRAINT [FK_BillDetail_Car] FOREIGN KEY([carId])
REFERENCES [dbo].[Car] ([carId])
GO
ALTER TABLE [dbo].[BillDetail] CHECK CONSTRAINT [FK_BillDetail_Car]
GO
ALTER TABLE [dbo].[Feedback]  WITH CHECK ADD  CONSTRAINT [FK_Feedback_BillDetail] FOREIGN KEY([billDetailId])
REFERENCES [dbo].[BillDetail] ([billDetailId])
GO
ALTER TABLE [dbo].[Feedback] CHECK CONSTRAINT [FK_Feedback_BillDetail]
GO
USE [master]
GO
ALTER DATABASE [CarRental] SET  READ_WRITE 
GO
