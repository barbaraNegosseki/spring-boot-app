package br.gov.sp.fatec.springbootapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.gov.sp.fatec.springbootapp.entity.Autorizacao;

public interface AutoriizacaoRepository extends JpaRepository<Autorizacao, Long> {
    
}
