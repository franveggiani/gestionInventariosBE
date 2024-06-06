package com.yarmovezzoli.gestioninv.Services;

import com.yarmovezzoli.gestioninv.Entities.Proveedor;
import com.yarmovezzoli.gestioninv.Repositories.BaseRepository;
import com.yarmovezzoli.gestioninv.Repositories.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedoresServiceImpl extends BaseServiceImpl<Proveedor,Long> implements ProveedorService {
    @Autowired
    private ProveedorRepository proveedorRepository;

    public ProveedoresServiceImpl(BaseRepository<Proveedor, Long> baseRepository) {
        super(baseRepository);
    }

    @Override
    public List<Proveedor> buscarPorNombre(String filtroNombre) throws Exception {
        try {
            List<Proveedor> proveedores = proveedorRepository.buscarPorNombre(filtroNombre);
            return proveedores;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
    public List<Proveedor> mostrarProveedores() throws  Exception{
        try {

            List<Proveedor> proveedores = proveedorRepository.findAll();
            return proveedores;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
