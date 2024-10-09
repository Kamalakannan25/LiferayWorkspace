//package com.demo.guestbook.portlet;
//
//import com.example.book.model.GuestbookEntry;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//import com.itextpdf.layout.Document;
//import com.itextpdf.layout.element.Paragraph;
//
//import java.io.ByteArrayOutputStream;
//import java.util.List;
//
//public class PDFGenerator {
//
//	public static byte[] generatePDF(List<GuestbookEntry> guestbookEntries) {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        PdfWriter writer = new PdfWriter(baos);
//        PdfDocument pdfDoc = new PdfDocument(writer);
//        Document document = new Document(pdfDoc);
//
//        for (GuestbookEntry entry : guestbookEntries) {
//            document.add(new Paragraph("ID: " + entry.getEntryId()));
//            document.add(new Paragraph("Name: " + entry.getName()));
//            document.add(new Paragraph("Message: " + entry.getMessage()));
//            document.add(new Paragraph("------"));
//        }
//
//        document.close();
//        return baos.toByteArray();
//    }
//}
