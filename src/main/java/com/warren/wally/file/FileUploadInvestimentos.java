package com.warren.wally.file;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.warren.wally.model.calculadora.TipoRentabilidade;
import com.warren.wally.model.investimento.TipoInvestimento;
import com.warren.wally.repository.ProdutoEntity;
import com.warren.wally.repository.ProdutoRepository;

@Component
public class FileUploadInvestimentos implements FileUpload {

	@Autowired
	private ProdutoRepository produtoRepository;

	@Override
	public void read(MultipartFile file) {
		try {
			XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			for (int r = 1; r < rows; r++) {
				XSSFRow row = sheet.getRow(r);
				if (row != null) {
					String instituicao = row.getCell(0).getStringCellValue();
					LocalDate vencimento = row.getCell(2).getDateCellValue().toInstant().atZone(ZoneId.systemDefault())
							.toLocalDate();
					System.out.println(row.getCell(4).getStringCellValue());
					System.out.println(TipoRentabilidade.valueOf(row.getCell(4).getStringCellValue()));
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
					produtoRepository.save(novoProduto);
				}
			}
			wb.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
