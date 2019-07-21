package com.warren.wally.model;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;

@Controller
public class ProdutoController {

	@Autowired
	private ProdutoRepository repository;
	
	@Autowired
	private MovimentacaoRepository movimentacaoRepository;

	@Autowired
	private MultiPortfolio multiportfolio;
	
	@Autowired
	private Portfolio portfolio;

	private LocalDate data = LocalDate.of(2018, 12, 10); // LocalDate.now();

	@RequestMapping("/")
	public String index(Model model) {
		GraficoTransformador graficoTransformador = new GraficoTransformador();

		model.addAttribute("variacao", multiportfolio.calculaVariacoes(data));
		model.addAttribute("patrimonioTotal", portfolio.getAccrual(data));
		model.addAttribute("proporcoes", graficoTransformador.transforma(portfolio.getProporcoes(data)));
		model.addAttribute("proporcoesRV", graficoTransformador.transforma(portfolio.getProporcoesRV(data)));
		model.addAttribute("instituicoes", graficoTransformador.transforma(portfolio.getPorInstituicoes(), true));
		model.addAttribute("liquidez", graficoTransformador.transforma(portfolio.getLiquidez(), true));
		return "index";
	}

	@RequestMapping("produtos")
	public String produtos(Model model) {
		List<Produto> produtos = new ArrayList<>();
		repository.findAll().forEach(entity -> {
			Produto produto = ProdutoFactory.getProduto(entity);
			if (produto != null) {
				produto.calculaAccrual(data);
				produtos.add(produto);
			}
		});
		model.addAttribute("produtos", produtos);
		model.addAttribute("hoje", data);
		return "produtos";
	}

	@RequestMapping(value = "salvar", method = RequestMethod.POST)
	public String salvar(@RequestParam(value = "corretora", required = false) String corretora,
			@RequestParam(value = "instituicao", required = false) String instituicao,
			@RequestParam(value = "tipoInvestimento", required = false) String tipoInvestimento,
			@RequestParam(value = "tipoRentabilidade", required = false) String tipoRentabilidade,
			@RequestParam(value = "vencimento", required = false) String vencimento,
			@RequestParam(value = "dtAplicacao", required = false) String dtAplicacao,
			@RequestParam(value = "taxa", required = false, defaultValue = "0.0") double taxa,
			@RequestParam(value = "valorAplicado", required = false, defaultValue = "0.0") double valorAplicado,
			@RequestParam(value = "arquivo", required = false) MultipartFile arquivo, Model model) {

		if (arquivo != null) {
			leArquivo(arquivo);
		} else {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

			ProdutoEntity novoProduto = new ProdutoEntity();
			novoProduto.setCorretora(corretora);
			novoProduto.setInstituicao(instituicao);
			novoProduto.setTipoInvestimento(TipoInvestimento.valueOf(tipoInvestimento));
			novoProduto.setTipoRentabilidade(TipoRentabilidade.valueOf(tipoRentabilidade));
			novoProduto.setVencimento(LocalDate.parse(vencimento, dtf));
			novoProduto.setDtAplicacao(LocalDate.parse(dtAplicacao, dtf));
			novoProduto.setTaxa(taxa);
			novoProduto.setValorAplicado(valorAplicado);
			repository.save(novoProduto);
		}

		List<Produto> produtos = new ArrayList<>();
		repository.findAll().forEach(entity -> {
			Produto produto = ProdutoFactory.getProduto(entity);
			if (produto != null) {
				produto.calculaAccrual(data);
				produtos.add(produto);
			}
		});
		model.addAttribute("produtos", produtos);
		model.addAttribute("hoje", data);

		return "produtos";
	}

	private void leArquivo(MultipartFile file) {
		try {

			XSSFWorkbook wb = new XSSFWorkbook(new File(file.getOriginalFilename()));
			XSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			for (int r = 1; r < rows; r++) {
				XSSFRow row = sheet.getRow(r);
				if (row != null) {
					String instituicao = row.getCell(0).getStringCellValue();
					LocalDate vencimento = row.getCell(2).getDateCellValue().toInstant().atZone(ZoneId.systemDefault())
							.toLocalDate();
					TipoInvestimento tipoInvestimento = TipoInvestimento.valueOf(row.getCell(3).getStringCellValue());
					TipoRentabilidade tipoRentabilidade = TipoRentabilidade
							.valueOf(row.getCell(4).getStringCellValue());
					double taxa = row.getCell(5).getNumericCellValue();
					String corretora = row.getCell(6).getStringCellValue();
					LocalDate dtAplicacao = row.getCell(7).getDateCellValue().toInstant().atZone(ZoneId.systemDefault())
							.toLocalDate();
					double valorAplicado = row.getCell(8).getNumericCellValue();

					ProdutoEntity novoProduto = new ProdutoEntity();
					novoProduto.setCorretora(corretora);
					novoProduto.setInstituicao(instituicao);
					novoProduto.setTipoInvestimento(tipoInvestimento);
					novoProduto.setTipoRentabilidade(tipoRentabilidade);
					novoProduto.setVencimento(vencimento);
					novoProduto.setDtAplicacao(dtAplicacao);
					novoProduto.setTaxa(taxa);
					novoProduto.setValorAplicado(valorAplicado);
					repository.save(novoProduto);
				}
			}
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}

	}
	
	private void leArquivoFII(MultipartFile file) {
		try {

			XSSFWorkbook wb = new XSSFWorkbook(new File(file.getOriginalFilename()));
			XSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			for (int r = 1; r < rows; r++) {
				XSSFRow row = sheet.getRow(r);
				if (row != null) {
					String codigo = row.getCell(0).getStringCellValue();
					LocalDate data = row.getCell(1).getDateCellValue().toInstant().atZone(ZoneId.systemDefault())
							.toLocalDate();
					double valorUnitario = row.getCell(3).getNumericCellValue();
					int quantidade = (int) row.getCell(2).getNumericCellValue();
	

					MovimentacaoEntity movimentacao = new MovimentacaoEntity();
					movimentacao.setCodigo(codigo);
					movimentacao.setData(data);
					movimentacao.setQuantidade(quantidade);
					movimentacao.setValorUnitario(valorUnitario);
					movimentacaoRepository.save(movimentacao);
				}
			}
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}

	}
	
	
	@RequestMapping(value = "salvarfii", method = RequestMethod.POST)
	public String salvarFII(@RequestParam(value = "arquivo", required = true) MultipartFile arquivo, Model model) {
		movimentacaoRepository.deleteAll();
		leArquivoFII(arquivo);
			
		List<Produto> produtos = new ArrayList<>();
		repository.findAll().forEach(entity -> {
			Produto produto = ProdutoFactory.getProduto(entity);
			if (produto != null) {
				produto.calculaAccrual(data);
				produtos.add(produto);
			}
		});
		model.addAttribute("produtos", produtos);
		model.addAttribute("hoje", data);

		return "produtos";
	}

}
