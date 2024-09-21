CREATE TABLE categories(
	id_category UUID,
	name VARCHAR(30) NOT NULL,
	
	CONSTRAINT PK_CATEGORY PRIMARY KEY(id_category)
);

CREATE TABLE products(
	id_product UUID,
    code VARCHAR(20) NOT NULL,
	name VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
	price NUMERIC(10,2) NOT NULL,
	
	CONSTRAINT PK_PRODUCT PRIMARY KEY(id_product)
);

CREATE TABLE img_urls(
	id_img_url UUID,
	url VARCHAR(255) NOT NULL,
	product_id UUID NOT NULL,

	CONSTRAINT PK_IMG_URL PRIMARY KEY(id_img_url),
	CONSTRAINT FK_PRODUCT_IMG_URL  FOREIGN KEY(product_id) REFERENCES products(id_product)
);

CREATE TABLE colors(
	id_color UUID,
	color VARCHAR(30),

	CONSTRAINT PK_COLOR PRIMARY KEY(id_color)
);

CREATE TABLE product_color(
	id_product UUID,
	id_color UUID,

	CONSTRAINT PK_PRODUCT_COLOR PRIMARY KEY(id_product, id_color),
	CONSTRAINT FK_PRODUCT_COLOR FOREIGN KEY(id_product) REFERENCES products(id_product),
	CONSTRAINT FK_COLOR_PRODUCT FOREIGN KEY(id_color) REFERENCES colors(id_color)
);

CREATE TABLE category_product(
	id_category UUID,
	id_product UUID,
	
	CONSTRAINT PK_CATEGORY_PRODUCT PRIMARY KEY(id_category, id_product),
	CONSTRAINT FK_CATEGORY_PRODUCT FOREIGN KEY(id_category) REFERENCES categories(id_category),
	CONSTRAINT FK_PRODUCT_CATEGORY FOREIGN KEY(id_product) REFERENCES products(id_product)
);