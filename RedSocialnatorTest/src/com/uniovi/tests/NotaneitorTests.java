package com.uniovi.tests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.services.DatosEjemplo;
import com.uniovi.tests.pageobjects.PO_HomeView;
import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_Properties;
import com.uniovi.tests.pageobjects.PO_RegisterView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método 
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NotaneitorTests {

	// En Windows (Debe ser la versión 46.0 y desactivar las actualizacioens
	// automáticas)):
	// static String PathFirefox = "C:\\Path\\FirefoxPortable.exe";
	static String PathFirefox = "P:\\SDI\\P5\\Firefox46.0.win\\Firefox46.win\\FirefoxPortable.exe";
	private static DatosEjemplo datosEjemplo = new DatosEjemplo();

	// Común a Windows y a MACOSX
	static WebDriver driver = getDriver(PathFirefox);
	static String URL = "http://localhost:8092";

	public static WebDriver getDriver(String PathFirefox) {
		// Firefox (Versión 46.0) sin geckodriver para Selenium 2.x.
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

//	@BeforeClass
//	public static void resetDatabase() {
//		driver.navigate().to(URL);
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999993D", "123456");
//		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
//	}

	@Test
	public void test() {
//		RegVal();
//		RegInval();
//		PR03();
//		PR04();
//		PR05();
//		PR07();
//		PR08();
//		PR09();
//		PR10();
//		PR11();
//		PR12();
//		PR13();
//		PR14();
//		PR15();

	}

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@Before
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@After
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeClass
	static public void begin() {
		//Cargar los usuarios antes de comenzar los tests ????????
		//datosEjemplo.init();
	}

	// Al finalizar la última prueba
	@AfterClass
	static public void end() { // Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	/*
	 * CASOS DE PRUEBA
	 */
	//1.1 [RegVal] Registro de Usuario con datos v�lidos.
	@Test
	public void RegVal() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "Josefo@gmail.com", "Josefo", "Perez", "123456", "123456");
		PO_View.checkElement(driver, "usuariosTitle", "Usuarios"); //No funciona, faltaria coincidir el campo con la internacionalizacion referent al id
	}

	//1.2 [RegInval] Registro de Usuario con datos inv�lidos (repetici�n de contrase�a invalida).
	@Test
	public void RegInval() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_RegisterView.fillForm(driver, "Josefo@gmail.com", "Josefo", "Perez", "12345", "12345");
		PO_View.checkElement(driver, "text-danger", "*{password}"); //No funciona, faltaria coincidir el campo con la internacionalizacion referent al id

	}

	//2.1 [InVal] Inicio de sesi�n con datos v�lidos.
	// home
	@Test
	public void InVal() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Josefo@gmail.com", "123456");
		//Comprueba que vea la lista de usuarios
		PO_View.checkElement(driver, "usuariosTitle", "#{text.usuariosDisponibles}");

		
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		PO_View.checkElement(driver, "usuariosTitle", "#{text.usuariosDisponibles}");
	}

	//2.2 [InInVal] Inicio de sesi�n con datos inv�lidos (usuario no existente en la aplicaci�n).
	// Español
	@Test
	public void InInVal() {
//		PO_HomeView.checkChangeIdiom(driver, "btnSpanish", "btnEnglish", PO_Properties.getSPANISH(),
//				PO_Properties.getENGLISH());
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Josefa@gmail.com", "123456");
		PO_View.checkElement(driver, "login", "Login"); //Lo hace el validator asi que en la pagina no se puede comprobar el error
		//Se puede checkear si est� algun elemento del login otra vez

	}

	//3.1 [LisUsrVal] Acceso al listado de usuarios desde un usuario en sesi�n.
	@Test
	public void LisUsrVal() {
		PO_HomeView.checkWelcome(driver, PO_Properties.getSPANISH());
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "Josefa@gmail.com", "123456");
		PO_View.checkElement(driver, "login", "Login");
		
		//LOGIN VALIDO
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary"); //Mirar como se hace mediante href ya que no hay bot�n
		PO_View.checkElement(driver, "login", "Login");
		

	}
//
//	// PR07. Loguearse con exito desde el ROl de Usuario, 99999990D, 123456
//	@Test
//	public void PR07() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999990A", "123456");
//		// COmprobamos que entramos en la pagina privada de Alumno
//		PO_View.checkElement(driver, "text", "Notas del usuario");
//	}
//
//	// PR08. Loguearse con exito desde el ROl de profesor, 99999993D/123456
//	@Test
//	public void PR08() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999993D", "123456");
//		// COmprobamos que entramos en la pagina privada de Alumno
//		PO_View.checkElement(driver, "text", "Notas del usuario");
//	}
//
//	// PR09. Loguearse con exito desde el ROl de Administrador, 99999988F/123456
//	@Test
//	public void PR09() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999988F", "123456");
//		// COmprobamos que entramos en la pagina privada de Alumno
//		PO_View.checkElement(driver, "text", "Notas del usuario");
//	}
//
//	// PR10. Loguearse sin exito desde el ROl de Usuario, 99999990A/123456
//	@Test
//	public void PR10() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "999999990A", "123456");
//
//		// Vamos a esperar 2 segundos por si no encuentra lo que se ha cargado
//		SeleniumUtils.esperarSegundos(driver, 2);
//
//		// COmprobamos que entramos en la pagina privada de Alumno
//		PO_View.checkElement(driver, "text", "Notas del usuario");
//	}
//
//	// PR11. Loguearse con exito desde el ROl de Alumno con desconexion,
//	// 99999990A/123456
//	@Test
//	public void PR11() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999990A", "123456");
//		// COmprobamos que entramos en la pagina privada de Alumno
//		PO_View.checkElement(driver, "text", "Notas del usuario");
//	}
//
//	// PR12. Loguearse, comprobar que se visualizan 4 filas de notas y desconectarse
//	// usando el rol de estudiante.
//	@Test
//	public void PR12() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario PO_LoginView.fillForm(driver, "99999990A" , "123456"
//		// );
//		// COmprobamos que entramos en la pagina privada de Alumno
//		PO_View.checkElement(driver, "text", "Notas del usuario");
//		// Contamos el número de filas de notas
//		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr",
//				PO_View.getTimeout());
//		assertTrue(elementos.size() == 4);
//		// Ahora nos desconectamos
//		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
//	}
//
//	// PR13. Loguearse como estudiante y ver los detalles de la nota con Descripcion
//	// = Nota A2.
//	// P13. Ver la lista de Notas.
//	@Test
//	public void PR13() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999990A", "123456");
//		// COmprobamos que entramos en la pagina privada de Alumno
//		PO_View.checkElement(driver, "text", "Notas del usuario");
//		SeleniumUtils.esperarSegundos(driver, 1);
//		// Contamos las notas
//		By enlace = By.xpath("//td[contains(text(), 'Nota A2')]/following-sibling::*[2]"); // Para evitar esto lo mejor
//																							// es poner id y nombre
//		driver.findElement(enlace).click();
//		SeleniumUtils.esperarSegundos(driver, 1);
//		// Esperamos por la ventana de detalle
//		PO_View.checkElement(driver, "text", "Detalles de la nota");
//		SeleniumUtils.esperarSegundos(driver, 1);
//		// Ahora nos desconectamos
//		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
//	}
//
//	// P14. Loguearse como profesor y Agregar Nota A2. //P14. Esta prueba podría
//	// encapsularse mejor ...
//	@Test
//	public void PR14() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999993D", "123456");
//		// COmprobamos que entramos en la pagina privada del Profesor
//		PO_View.checkElement(driver, "text", "99999993D");
//		// Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
//		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
//		elementos.get(0).click();
//		// Esperamos a aparezca la opción de añadir nota: //a[contains(@href,
//		// 'mark/add')]
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/add')]");
//		// Pinchamos en agregar Nota.
//		elementos.get(0).click();
//		// Ahora vamos a rellenar la nota. //option[contains(@value, '4')]
//		PO_PrivateView.fillFormAddMark(driver, 3, "Nota Nueva 1", "8");
//		// Esperamos a que se muestren los enlaces de paginación la lista de notas
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'page-link')]");
//		// Nos vamos a la última página
//		elementos.get(3).click();
//		// Comprobamos que aparece la nota en la pagina
//		elementos = PO_View.checkElement(driver, "text", "Nota Nueva 1");
//		// Ahora nos desconectamos
//		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
//	}
//
//	// PRN. Loguearse como profesor, vamos a la ultima página y Eliminamos la Nota
//	// Nueva 1.
//	// PRN. Ver la lista de Notas.
//	@Test
//	public void PR15() {
//		// Vamos al formulario de logueo.
//		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
//		// Rellenamos el formulario
//		PO_LoginView.fillForm(driver, "99999993D", "123456");
//		// COmprobamos que entramos en la pagina privada del Profesor
//		PO_View.checkElement(driver, "text", "99999993D");
//		// Pinchamos en la opción de menu de Notas: //li[contains(@id, 'marks-menu')]/a
//		List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'marks-menu')]/a");
//		elementos.get(0).click();
//		// Pinchamos en la opción de lista de notas.
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@href, 'mark/list')]");
//		elementos.get(0).click();
//		// Esperamos a que se muestren los enlaces de paginacion la lista de notas
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'pagelink')]");
//		// Nos vamos a la última página
//		elementos.get(3).click();
//		// Esperamos a que aparezca la Nueva nota en la ultima pagina
//
//		// Y Pinchamos en el enlace de borrado de la Nota "Nota Nueva 1"
//		// //td[contains(text(), 'Nota Nueva
//		// 1')]/following-sibling::*/a[contains(text(), 'mark/delete')]"
//		elementos = PO_View.checkElement(driver, "free",
//				"//td[contains(text(), 'Nota Nueva 1')]/following-sibling::*/a[contains(@href, 'mark/delete')]");
//		elementos.get(0).click();
//		// Volvemos a la última pagina
//		elementos = PO_View.checkElement(driver, "free", "//a[contains(@class, 'pagelink')]");
//		elementos.get(3).click();
//		// Y esperamos a que NO aparezca la ultima "Nueva Nota 1"
//		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "Nota Nueva 1", PO_View.getTimeout());
//		// Ahora nos desconectamos
//		PO_PrivateView.clickOption(driver, "logout", "text", "Identifícate");
//	}
}
