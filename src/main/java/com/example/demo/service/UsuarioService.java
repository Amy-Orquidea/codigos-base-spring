package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UsuarioService {
    @Autowired
    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> buscarComentarios() {
        return usuarioRepository.findAll();
    }

    public Usuario buscarPorId(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Comentário não encontrado com ID:" + id));
    }

    public Usuario salvarComentario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public void excluirComentarioPorId(Integer id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Comentário não encontrado com ID:" + id);
        } else {
            usuarioRepository.deleteById(id);
        }
    }

    public Usuario salvarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public Usuario autenticar(String email, String senha) {
        return usuarioRepository.findByEmailAndSenha(email, senha);
    }

    public boolean emailJaExiste(String email) {
        return usuarioRepository.findByEmail(email) != null;
    }

    //     public Especie salvarEspecie(Especie especie) {
    //     if (especie.getId() != null) {
    //         Especie existente = buscarPorIdOuFalhar(especie.getId());
    //         existente.setNome(especie.getNome());
    //         return especieRepository.save(existente);
    //     } else {
    //         return especieRepository.save(especie);
    //     }
    // }

    //    public Raca salvarRaca(Raca raca) {
    //     // Validar e anexar Especie
    //     if (raca.getEspecie() == null || raca.getEspecie().getId() == null) {
    //         throw new IllegalArgumentException("Espécie é obrigatória para a raça.");
    //     }
    //     Especie especie = especieService.buscarPorIdOuFalhar(raca.getEspecie().getId());
    //     raca.setEspecie(especie);

    //     if (raca.getId() != null) {
    //         Raca existente = buscarPorId(raca.getId());
    //         existente.setNome(raca.getNome());
    //         existente.setEspecie(raca.getEspecie()); // Atualiza especie
    //         return racaRepository.save(existente);
    //     } else {
    //         return racaRepository.save(raca);
    //     }
    // }
}
