
package com.Tienda.controller;

import com.Tienda.domain.Articulo;
import com.Tienda.domain.CarritoDetalle;
import com.Tienda.service.ArticuloService;
import com.Tienda.service.CarritoDetalleService;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class CarritoController {
    
    @Autowired
    CarritoDetalleService carritoDetalleService;
    
    @Autowired
    ArticuloService articuloService;
 
    @GetMapping("/carrito/agregar/{idArticulo}")
    public String agregar(Articulo articulo, HttpSession session){
        Long idCarrito = (Long)session.getAttribute("idCarrito");
        
        articulo = articuloService.getArticulo(articulo);
        
        CarritoDetalle carritoDetalle = carritoDetalleService.getCarritoDetalle(idCarrito, articulo);
        if(carritoDetalle != null){
            carritoDetalle.setCantidad(carritoDetalle.getCantidad()+1);
        }else{
            carritoDetalle = new CarritoDetalle(idCarrito, articulo, articulo.getPrecio(), 1);
        }
        
        carritoDetalleService.save(carritoDetalle);
        
        return "redirect:/";
    }
    
    @GetMapping("/carrito/listado")
    public String listado(Model model, HttpSession session){
        Long idCarrito = (Long)session.getAttribute("idCarrito");
        boolean esCliente = (boolean)session.getAttribute("esCliente");
        
        List<CarritoDetalle> carritoDetalles = carritoDetalleService.getCarritoDetalles(idCarrito);
        model.addAttribute("cantidadArticulosCarrito", carritoDetalles.size());
        
        double montoTotal = 0.0;
        double montoImpuestos = 0.0;
        
        for(CarritoDetalle c : carritoDetalles){
            montoTotal += c.getPrecio() * c.getCantidad();
        }
        montoImpuestos=montoTotal * 0.15;
        
        model.addAttribute("carritoDetalles", carritoDetalles);
        model.addAttribute("cantidadArticuloCarrito", carritoDetalles.size());
        model.addAttribute("esCliente", esCliente);
        model.addAttribute("montoImpuestos", montoImpuestos);
        model.addAttribute("montoTotal", montoTotal);
        return "/carrito/listado";
    }
    
}
