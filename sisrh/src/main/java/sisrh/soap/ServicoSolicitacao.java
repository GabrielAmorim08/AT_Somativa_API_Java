package sisrh.soap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import sisrh.banco.Banco;
import sisrh.dto.Empregado;
import sisrh.dto.Empregados;
import sisrh.dto.Solicitacao;
import sisrh.dto.Solicitacoes;
import sisrh.seguranca.Autenticador;

@WebService
@SOAPBinding(style = Style.RPC)
public class ServicoSolicitacao {
	@Resource
	WebServiceContext context;
	
	@WebMethod(action = "listar")
	public Solicitacoes listar() throws Exception {
		
		Autenticador.autenticarUsuarioSenha(context);
		
		String usuario = Autenticador.getUsuario(context);
		
		Solicitacoes solicitacao = new Solicitacoes();
		
		List<Solicitacao> lista = Banco.listarSolicitacoes(usuario);
		for(Solicitacao sol: lista) {
			solicitacao.getEmpregados().add(sol);
		}
		return solicitacao;
	}
}