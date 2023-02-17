
package com.Tienda.controller;

import com.Tienda.dao.ClienteDao;
import com.Tienda.domain.Cliente;
import com.Tienda.service.ClienteService;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
@Slf4j
@Controller
public class IndexController {
    @Autowired
    ClienteService clienteService;
    @GetMapping("/")
    public String inicio(Model model) {
       /* log.info("Ahora desde MVC");
        model.addAttribute("mensaje","Hola desde el controlador");
        
        Cliente cliente = new Cliente("Sebastian","Briones Robles","sbriones284@gmail.com","72076338");
        model.addAttribute("objetoCliente", cliente);
        
        Cliente cliente2 = new Cliente("Juan","Horacio Esquivel","juan@gmail.com","65747832");
        Cliente cliente3 = new Cliente("Gustavo","Ramos Sancho","Gustavo@gmail.com","85459632");
        
        List<Cliente> clientes = Arrays.asList(cliente,cliente2,cliente3);*/
       var clientes = clienteService.getClientes();
       
        model.addAttribute("clientes", clientes);
        return "Index";
    }
    @GetMapping("/nuevoCliente")
    public String nuevoCliente(Cliente cliente){
        return "modificarCliente";
    }
    @PostMapping("/guardarCliente")
    public String guardarCliente(Cliente cliente){
        clienteService.save(cliente);
        return "redirect:/";
    }
    @GetMapping("/modificarCliente/{idCliente}")
    public String modificarCliente(Cliente cliente, Model model){
        cliente = clienteService.getCliente(cliente);
        model.addAttribute("cliente", cliente);
        return "modificarCliente";
    }
    @GetMapping("/eliminarCliente/{idCliente}")
    public String elminarCliente(Cliente cliente){
        clienteService.delete(cliente);
        return "redirect:/";
    }
}