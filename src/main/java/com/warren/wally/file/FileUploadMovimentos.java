package com.warren.wally.file;

import java.time.LocalDate;
import java.time.ZoneId;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.warren.wally.repository.DividendoEntity;
import com.warren.wally.repository.DividendoRepository;
import com.warren.wally.repository.MovimentacaoEntity;
import com.warren.wally.repository.MovimentacaoRepository;

@Component
public class FileUploadMovimentos implements FileUpload {

	@Autowired
	private MovimentacaoRepository movimentacaoRepository;

	@Autowired
	private DividendoRepository dividendoRepository;

	@Override
	public void read(MultipartFile file) {
		try {

			XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = wb.getSheetAt(0);
			int rows = sheet.getPhysicalNumberOfRows();

			for (int r = 1; r < rows; r++) {
				XSSFRow row = sheet.getRow(r);
				try {
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
				} catch (Exception e) {
					System.out.println("Erro na linha" + r);
				}
			}

			XSSFSheet sheet2 = wb.getSheetAt(1);
			int rows2 = sheet2.getPhysicalNumberOfRows();

			for (int r = 1; r < rows2; r++) {
				XSSFRow row = sheet2.getRow(r);
				try {
					String codigo = row.getCell(0).getStringCellValue();
					LocalDate data = row.getCell(1).getDateCellValue().toInstant().atZone(ZoneId.systemDefault())
							.toLocalDate();
					double valorUnitario = row.getCell(3).getNumericCellValue();
					int quantidade = (int) row.getCell(2).getNumericCellValue();

					DividendoEntity dividendo = new DividendoEntity();
					dividendo.setCodigo(codigo);
					dividendo.setData(data);
					dividendo.setQuantidade(quantidade);
					dividendo.setValorUnitario(valorUnitario);
					dividendoRepository.save(dividendo);
				} catch (Exception e) {
					System.out.println("Erro na linha" + r);
				}
			}
			wb.close();
		} catch (Exception ioe) {
			ioe.printStackTrace();
		}

	}

}
