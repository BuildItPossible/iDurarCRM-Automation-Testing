package iDurarERP;

import org.testng.annotations.DataProvider;

public class dataProvider {

    @DataProvider(name = "CompanyData")
    public Object[][] newCompanies() {
        return new Object[][]{
                {"TestCompany One", "Albania", "1234567890", "testcompany1@email.com", "www.test1.website"},
                {"TestCompany two", "India", "1234567890", "testcompany2@email.com", "www.test2.website"},
                {"TestCompany three", "China", "1234567890", "testcompany2@email.com", "www.test3.website"},
                {"TestCompany four", "Bangladesh", "1234567890", "testcompany2@email.com", "www.test4.website"},
                {"TestCompany five", "Japan", "1234567890", "testcompany2@email.com", "www.test5.website"},
        };
    }

    @DataProvider(name = "CategoryData")
    public Object[][] newCategory() {
        return new Object[][]{
                {"Category One", "Category One Description", "green", true},
                {"Category two", "Category Two Description", "orange", true},
                {"Category three", "Category Three Description", "blue", true},
                {"Category four", "Category Four Description", "red", false},
                {"Category five", "Category Five Description", "red", false},
        };
    }

    @DataProvider(name = "ProductData")
    public Object[][] newProduct() {
        return new Object[][]{
                {"Product One", "12.50", "Category One Description", "Ref One"},
                {"Product Two", "15", "Category Two Description", "Ref Two"},
                {"Product Three", "10.25", "Category Three Description", "Ref One"},
                {"Product Four",  "300", "Category Four Description", "Ref Three"},
                {"Product Five",  "1000", "Category Five Description", "Ref Two"},
        };
    }

    @DataProvider(name = "InvoiceData")
    public static Object[][] newInvoice() {
        return new Object[][]{
                {"Item One", "Category One Description", "2", "12.50"},
                {"Item Two", "Category Two Description","4", "15"},
                {"Item Three", "Category Three Description", "5", "10.25"},
                {"Item Four",  "Category Four Description","1", "300"},
                {"Item Five", "Category Five Description", "1", "1000"},
                {"Item Six", "Category Six Description", "1", "230"},
        };
    }



}
