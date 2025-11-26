package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.model.Usuario;
import com.example.demo.service.UsuarioService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;

public class UsuarioController {

    @Autowired
    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Página inicial - redireciona para login
    @GetMapping("/")
    public String home() {
        return "redirect:/login";
    }

    // Exibir página de login
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Processar login
    @PostMapping("/login")
    public String processarLogin(@RequestParam String email,
            @RequestParam String senha,
            HttpSession session,
            Model model) {

        Usuario usuario = usuarioService.autenticar(email, senha);

        if (usuario != null) {
            session.setAttribute("usuarioLogado", usuario);
            return "redirect:/dashboard";
        } else {
            model.addAttribute("erro", "Email ou senha inválidos!");
            return "login";
        }
    }

    // Exibir página de cadastro
    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }

    // Processar cadastro
    @PostMapping("/cadastro")
    public String processarCadastro(@RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            Model model) {

        if (usuarioService.emailJaExiste(email)) {
            model.addAttribute("erro", "Email já cadastrado!");
            return "cadastro";
        }

        Usuario novoUsuario = new Usuario(email, senha, nome);
        usuarioService.salvarUsuario(novoUsuario);

        model.addAttribute("sucesso", "Usuário cadastrado com sucesso!");
        return "login";
    }

    // Dashboard (área logada)
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");

        if (usuario == null) {
            return "redirect:/login";
        }

        model.addAttribute("usuario", usuario);
        return "dashboard";
    }

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    //     @GetMapping("/cadastro")
    // public String exibirFormularioCadastro(Model model) {
    //     model.addAttribute("raca", new Raca());
    //     model.addAttribute("especies", especieService.buscarTodasAsEspecies());
    //     return "racas/cadastro";
    // }

    // @PostMapping
    // public String salvarRaca(@ModelAttribute Raca raca, @RequestParam("especieId") Integer especieId, Model model,
    //         RedirectAttributes redirectAttributes) {
    //     try {
    //         Especie especie = especieService.buscarPorIdOuFalhar(especieId);
    //         raca.setEspecie(especie);
    //         racaService.salvarRaca(raca);
    //         redirectAttributes.addFlashAttribute("mensagemSucesso", "Raça salva com sucesso!");
    //         return "redirect:/racas";
    //     } catch (Exception e) {
    //         redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao salvar raça: " + e.getMessage());
    //         model.addAttribute("raca", raca);
    //         model.addAttribute("especies", especieService.buscarTodasAsEspecies());
    //         return "racas/cadastro";
    //     }
    // }

    //     @GetMapping("/editar/{id}")
    // public String exibirFormularioEdicao(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
    //     try {
    //         Especie especie = especieService.buscarPorIdOuFalhar(id);
    //         model.addAttribute("especie", especie);
    //         return "especies/editar";
    //     } catch (EntityNotFoundException e) {
    //         redirectAttributes.addFlashAttribute("mensagemErro", "Espécie não encontrada com ID: " + id);
    //         return "redirect:/especies";
    //     }
    // }

    // @PostMapping("/editar/{id}")
    // public String atualizarEspecie(@PathVariable Integer id, @ModelAttribute Especie especie,
    //         RedirectAttributes redirectAttributes) {
    //     try {
    //         especie.setId(id);
    //         especieService.salvarEspecie(especie);
    //         redirectAttributes.addFlashAttribute("mensagemSucesso", "Espécie atualizada com sucesso!");
    //         return "redirect:/especies";
    //     } catch (Exception e) {
    //         redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao atualizar espécie: " + e.getMessage());
    //         return "redirect:/especies/editar/" + id;
    //     }
    // }

    // @GetMapping("/deletar/{id}")
    // public String deletarEspecie(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
    //     try {
    //         especieService.excluirEspeciePorId(id);
    //         redirectAttributes.addFlashAttribute("mensagemSucesso", "Espécie excluída com sucesso!");
    //     } catch (Exception e) {
    //         redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao excluir espécie: " + e.getMessage());
    //     }
    //     return "redirect:/especies";
    // }
}
