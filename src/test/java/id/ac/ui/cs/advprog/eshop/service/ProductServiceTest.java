package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {}
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("5ba5eee5-99a4-43e2-9be2-26f02557d741");
        product.setProductName("Lumba Lumba Asli Jawa");
        product.setProductQuantity(100);
        productService.create(product);

        List<Product> productList = productService.findAll();
        assertFalse(productList.isEmpty());
        Product savedProduct = productList.getFirst();

        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testCommitEditAndCheck() {
        Product product = new Product();
        product.setProductId("5ba5eee5-99a4-43e2-9be2-26f02557d741");
        product.setProductName("Lumba Lumba Asli Jawa");
        product.setProductQuantity(100);
        productService.create(product);

        Product editedProduct = new Product();
        editedProduct.setProductId(product.getProductId());
        editedProduct.setProductName("Lumba Lumba Asli Lampung");
        editedProduct.setProductQuantity(99);
        productService.commitEdit(editedProduct);

        assertNotEquals("Lumba Lumba Asli Jawa", product.getProductName());
        assertEquals("Lumba Lumba Asli Lampung", product.getProductName());

        assertNotEquals(100, product.getProductQuantity());
        assertEquals(99, product.getProductQuantity());
    }

    @Test
    void testCommitEditAndFail() {
        Product product = new Product();
        product.setProductId("5ba5eee5-99a4-43e2-9be2-26f02557d741");
        product.setProductName("Lumba Lumba Asli Jawa");
        product.setProductQuantity(100);
        productService.create(product);

        Product editedProduct = new Product();
        editedProduct.setProductId("6ca5eee5-99a4-43e2-9be2-26f02557d741");
        editedProduct.setProductName("Lumba Lumba Asli Lampung");
        editedProduct.setProductQuantity(99);
        productService.commitEdit(editedProduct);

        assertEquals("Lumba Lumba Asli Jawa", product.getProductName());
        assertNotEquals("Lumba Lumba Asli Lampung", product.getProductName());

        assertEquals(100, product.getProductQuantity());
        assertNotEquals(99, product.getProductQuantity());
    }

    @Test
    void testDeleteAndFindAll() {
        Product product = new Product();
        product.setProductId("5ba5eee5-99a4-43e2-9be2-26f02557d741");
        product.setProductName("Lumba Lumba Asli Jawa");
        product.setProductQuantity(100);
        productService.create(product);
        productService.delete(product);

        List<Product> productIterator = productService.findAll();
        assertTrue(productIterator.isEmpty());
    }

    @Test
    void testDeleteAndFindById() {
        Product product = new Product();
        product.setProductId("5ba5eee5-99a4-43e2-9be2-26f02557d741");
        product.setProductName("Lumba Lumba Asli Jawa");
        product.setProductQuantity(100);
        productService.create(product);
        productService.delete(product);

        Product foundProductById = productService.findById(product.getProductId());
        assertNull(foundProductById);
    }

    @Test
    void testFindAllIfEmpty() {
        List<Product> productIterator = productService.findAll();
        assertTrue(productIterator.isEmpty());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("93e4a6d8-a596-4e9e-bd30-25a80b676740");
        product1.setProductName("Lumba Lumba Asli Sumatra");
        product1.setProductQuantity(120);
        productService.create(product1);

        Product product2 = new Product();
        product2.setProductId("8539355a-e19c-4402-86f5-f93ecd5945b2");
        product2.setProductName("Lumba Lumba Asli Sulawesi");
        product2.setProductQuantity(80);
        productService.create(product2);

        List<Product> productList = productService.findAll();
        assertFalse(productList.isEmpty());

        for (int i=0; i<2; i++) {
            if (i == 0) {
                assertEquals(product1.getProductId(), productList.getFirst().getProductId());
            } else if (i == 1) {
                assertEquals(product2.getProductId(), productList.get(1).getProductId());
            }
        }
    }

    @Test
    void testFindByIdIfEmpty() {
        assertNull(productService.findById("5ba5eee5-99a4-43e2-9be2-26f02557d741"));
    }

    @Test
    void testFindByIdIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("93e4a6d8-a596-4e9e-bd30-25a80b676740");
        product1.setProductName("Lumba Lumba Asli Sumatra");
        product1.setProductQuantity(120);
        productService.create(product1);

        Product product2 = new Product();
        product2.setProductId("8539355a-e19c-4402-86f5-f93ecd5945b2");
        product2.setProductName("Lumba Lumba Asli Sulawesi");
        product2.setProductQuantity(80);
        productService.create(product2);

        Product product3 = new Product();
        product3.setProductId("f77a6d7c-cbd5-44c3-9f06-70064444f8e5");
        product3.setProductName("Lumba Lumba Asli Papua");
        product3.setProductQuantity(20);
        productService.create(product3);

        assertNotNull(productService.findById("f77a6d7c-cbd5-44c3-9f06-70064444f8e5"));
        assertNull(productService.findById("g77a6d7c-cbd5-44c3-9f06-70064444f8e6"));
    }
}
