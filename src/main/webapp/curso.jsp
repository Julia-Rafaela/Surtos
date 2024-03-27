<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <link rel="stylesheet" href="./webapp/style.css">
    <title>Curso</title>
</head>
<body class="tela_aluno">
<div class="menu">
    <jsp:include page="menu.jsp"></jsp:include>
</div>
<br />
<div align="center" class="container">
    <form action="curso" method="post">
        <p class="title"></p>
        <p class="cadastrar">Cadastrar</p>
        <table>
            <tr>
                <td class="aluno" colspan="4">
                    <p class="title">Codigo:</p> <input class="cadastro" type="number" id="codigo" name="codigo" placeholder="" value="${curso.codigo}" /> <input type="submit" id="botao" name="botao" value="Buscar">
                </td>
            </tr>
            <tr>
                <td class="aluno" colspan="4">
                    <p class="title">Nome:</p> <input class="cadastro" type="text" id="nome" name="nome" placeholder="" value="${curso.nome}" />
                </td>
            </tr>
            <tr>
                <td class="aluno" colspan="4">
                    <p class="title">Carga Horaria:</p> <input class="cadastro" type="text" id="carga_horaria" name="carga_horaria" placeholder="" value="${curso.carga_horaria}" />
                </td>
            </tr>
            <tr>
                <td class="aluno" colspan="4">
                    <p class="title">Sigla:</p> <input class="cadastro" type="text" id="sigla" name="sigla" value="${curso.sigla}" />
                </td>
            </tr>
            <tr>
                <td class="aluno" colspan="4">
                    <p class="title">Nota ENADE:</p> <input class="cadastro" type="text" id="nota_enade" name="nota_enade" value="${curso.nota_enade}" />
                </td>
            </tr>
            <tr class="botoes">
                <td><input type="submit" id="botao" name="botao" value="Cadastrar"></td>
                <td><input type="submit" id="botao" name="botao" value="Alterar"></td>
                <td><input type="submit" id="botao" name="botao" value="Excluir"></td>
                <td><input type="submit" id="botao" name="botao" value="Listar"></td>
            </tr>
        </table>
    </form>
</div>
<br />
<div align="center">
    <c:if test="${not empty erro}">
        <H2>
            <b><c:out value="${erro}" /></b>
        </H2>
    </c:if>
</div>
<div class="mensagem" align="center">
    <c:if test="${not empty saida}">
        <H3>
            <b><c:out value="${saida}" /></b>
        </H3>
    </c:if>
</div>
<br />
<c:if test="${not empty cursos}">
    <table class="table_round">
        <thead>
            <tr>
                <th>Código</th>
                <th>Nome</th>
                <th>Carga Horária</th>
                <th>Sigla</th>
                <th>Nota ENADE</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="c" items="${cursos}">
                <tr>
                    <td><c:out value="${c.codigo }" /></td>
                    <td><c:out value="${c.nome }" /></td>
                    <td><c:out value="${c.carga_horaria }" /></td>
                    <td><c:out value="${c.sigla }" /></td>
                    <td><c:out value="${c.nota_enade }" /></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>
</body>

<style>
.table_round {
	width: 50%;
	border-collapse: collapse;
	margin-bottom: 20px;
	background-color: #f0f0f0;
	font-size: 12px;
}
</style>
</html>
