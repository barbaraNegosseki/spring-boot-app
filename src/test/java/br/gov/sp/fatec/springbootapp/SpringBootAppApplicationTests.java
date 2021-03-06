package br.gov.sp.fatec.springbootapp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import br.gov.sp.fatec.springbootapp.entity.Autorizacao;
import br.gov.sp.fatec.springbootapp.entity.Usuario;
import br.gov.sp.fatec.springbootapp.repository.AutoriizacaoRepository;
import br.gov.sp.fatec.springbootapp.repository.UsuarioRepository;
import br.gov.sp.fatec.springbootapp.service.SegurancaService;

@SpringBootTest
@Transactional
@Rollback
class SpringBootAppApplicationTests {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private AutoriizacaoRepository autRepo;

    @Autowired
    private SegurancaService segService;

	@Test
	void contextLoads() {
    }
    
    @Test
    void testaInsercao() {
        Usuario usuario = new Usuario();
        usuario.setNome("Usuario");
        usuario.setSenha("senha1");
        usuario.setAutorizacoes(new HashSet<Autorizacao>());

        Autorizacao aut = new Autorizacao();
        aut.setNome("ROLE_USUARIO");
        autRepo.save(aut);
        usuario.getAutorizacoes().add(aut);
        usuarioRepo.save(usuario);
        assertNotNull(usuario.getAutorizacoes().iterator().next().getId());
    }

    @Test
    void testaInsercaoDeAutorizacao() {
        Usuario usuario = new Usuario();
        usuario.setNome("Usuario2");
        usuario.setSenha("senha1");
        usuarioRepo.save(usuario);

        Autorizacao aut = new Autorizacao();
        aut.setNome("ROLE_USUARIO2");
        aut.setUsuarios(new HashSet<Usuario>());
        aut.getUsuarios().add(usuario);

        autRepo.save(aut);
        //usuario.getAutorizacoes().add(aut);
        assertNotNull(aut.getUsuarios().iterator().next().getId());
    }
    
    @Test
    void testaAutorizacao() {
        Usuario usuario = usuarioRepo.findById(1L).get();
        assertEquals("ROLE_ADMIN", usuario.getAutorizacoes().iterator().next().getNome());
    }

    @Test
    void testaUsuario() {
        Autorizacao aut = autRepo.findById(1L).get();
        assertEquals("Barbara", aut.getUsuarios().iterator().next().getNome());
    }

    @Test
    void testaBuscaUsuarioNomeContains(){
        List<Usuario> usuarios = usuarioRepo.findByNomeContainsIgnoreCase("B");
        assertFalse(usuarios.isEmpty());
    }

    @Test
    void testaBuscaUsuarioNome(){
        Usuario usuario = usuarioRepo.findByNome("Barbara");
        assertNotNull(usuario);
    }

    @Test
    void testaBuscaUsuarioNomeSenha(){
        Usuario usuario = usuarioRepo.findByNomeAndSenha("Barbara", "pass123");
        assertNotNull(usuario);
    }

    @Test
    void testaBuscaUsuarioNomeAutorizacao(){
        List<Usuario> usuarios = usuarioRepo.findByAutorizacoesNome("ROLE_ADMIN");
        assertFalse(usuarios.isEmpty());
    }

    //utilizando queries

    @Test
    void testaBuscaUsuarioNomeQuery(){
        Usuario usuario = usuarioRepo.buscaUsuarioPorNome("Barbara");
        assertNotNull(usuario);
    }

    @Test
    void testaBuscaUsuarioNomeSenhaQuery(){
        Usuario usuario = usuarioRepo.buscaUsuarioPorNomeSenha("Barbara", "pass123");
        assertNotNull(usuario);
    }

    @Test
    void testaBuscaUsuarioNomeAutorizacaoQuery(){
        List<Usuario> usuarios = usuarioRepo.buscaPorNomeAutorizacao("ROLE_ADMIN");
        assertFalse(usuarios.isEmpty());
    }

    @Test
    void testaServivoCriaUsuario() {
        Usuario usuario = segService.criarUsuario("Normal", "senha123", "ROLE_USUARIO");
        assertNotNull(usuario);
    }
}
