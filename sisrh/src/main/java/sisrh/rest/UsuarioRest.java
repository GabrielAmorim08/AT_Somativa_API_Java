package sisrh.rest;

import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.core.Response.Status;
import io.swagger.annotations.Api;
import sisrh.banco.Banco;
import sisrh.dto.Usuario;

@Api
@Path("/usuario")
public class UsuarioRest {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuarios() {
        try {
            List<Usuario> lista = Banco.listarUsuarios();
            GenericEntity<List<Usuario>> entity = new GenericEntity<List<Usuario>>(lista) {};
            return Response.ok().entity(entity).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity("{ \"mensagem\" : \"Falha ao listar usuarios\" , \"detalhe\" : \"" + e.getMessage() + "\" }")
                .build();
        }
    }

    @GET
    @Path("{matricula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obterUsuario(@PathParam("matricula") String matricula) {
        try {
            Usuario u = Banco.buscarUsuarioPorMatricula(matricula);
            if (u != null) {
                return Response.ok().entity(u).build();
            } else {
                return Response.status(Status.NOT_FOUND)
                        .entity("{ \"mensagem\" : \"Usuario nao encontrado!\" }").build();
            }
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("{ \"mensagem\" : \"Falha para obter usuario!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response incluirUsuario(Usuario usuario) {
        try 
        {
            Usuario u = Banco.incluirUsuario(usuario);
            return Response.ok().entity(u).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("{ \"mensagem\" : \"Falha na inclusao do usuario!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
        }
    }

    @PUT
    @Path("{matricula}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response alterarUsuario(@PathParam("matricula") String matricula, Usuario usuario)  {
        try {
            if ( Banco.buscarUsuarioPorMatricula(matricula) == null ) {
                return Response.status(Status.NOT_FOUND)
                        .entity("{ \"mensagem\" : \"Usuario nao encontrado!\" }").build();
            }
            Usuario u = Banco.alterarUsuario(matricula, usuario);
            return Response.ok().entity(u).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("{ \"mensagem\" : \"Falha na alteracao do usuario!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
        }
    }

    @DELETE
    @Path("{matricula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response excluirUsuario(@PathParam("matricula") String matricula) {
        try {
            if ( Banco.buscarUsuarioPorMatricula(matricula) == null ) {
                return Response.status(Status.NOT_FOUND)
                        .entity("{ \"mensagem\" : \"Usuario nao encontrado!\" }").build();
            }
            Banco.excluirUsuario(matricula);
            return Response.ok().entity("{ \"mensagem\" : \"Usuario " + matricula + " excluido!\" }").build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("{ \"mensagem\" : \"Falha na exclusao do usuario!\" , \"detalhe\" :  \""+ e.getMessage() +"\"  }").build();
        }
    }
}
