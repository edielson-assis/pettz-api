CREATE TABLE categories(
	category_id UUID,
	name VARCHAR(30) NOT NULL,
	
	CONSTRAINT PK_CATEGORY PRIMARY KEY(category_id)
);

CREATE TABLE products(
	product_id UUID,
    code VARCHAR(20) NOT NULL,
	name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
	price NUMERIC(10,2) NOT NULL,
	
	CONSTRAINT PK_PRODUCT PRIMARY KEY(product_id)
);

CREATE TABLE img_urls(
	img_url_id UUID,
	url VARCHAR(255) NOT NULL,
	product_id UUID NOT NULL,

	CONSTRAINT PK_IMG_URL PRIMARY KEY(img_url_id),
	CONSTRAINT FK_PRODUCT_IMG_URL  FOREIGN KEY(product_id) REFERENCES products(product_id)
);

CREATE TABLE colors(
	color_id UUID,
	color VARCHAR(30),

	CONSTRAINT PK_COLOR PRIMARY KEY(color_id)
);

CREATE TABLE product_color(
	product_id UUID,
	color_id UUID,

	CONSTRAINT PK_PRODUCT_COLOR PRIMARY KEY(product_id, color_id),
	CONSTRAINT FK_PRODUCT_COLOR FOREIGN KEY(product_id) REFERENCES products(product_id),
	CONSTRAINT FK_COLOR_PRODUCT FOREIGN KEY(color_id) REFERENCES colors(color_id)
);

CREATE TABLE category_product(
	category_id UUID,
	product_id UUID,
	
	CONSTRAINT PK_CATEGORY_PRODUCT PRIMARY KEY(category_id, product_id),
	CONSTRAINT FK_CATEGORY_PRODUCT FOREIGN KEY(category_id) REFERENCES categories(category_id),
	CONSTRAINT FK_PRODUCT_CATEGORY FOREIGN KEY(product_id) REFERENCES products(product_id)
);