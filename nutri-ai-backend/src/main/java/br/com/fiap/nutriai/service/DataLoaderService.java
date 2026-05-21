package br.com.fiap.nutriai.service;

import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DataLoaderService implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(DataLoaderService.class);

    private final VectorStore vectorStore;

    public DataLoaderService(VectorStore vectorStore) {
        this.vectorStore = vectorStore;
    }

    @Override
    public void run(ApplicationArguments args) {
        List<Document> documents = List.of(
                new Document("Proteínas são macronutrientes essenciais compostos por aminoácidos. São fundamentais para construção e reparo muscular, síntese de enzimas e hormônios. Fontes de alta qualidade: carnes magras, ovos, laticínios, peixes, leguminosas e soja. A ingestão recomendada varia de 0.8g/kg (sedentários) a 2.2g/kg (atletas de força) por kg de peso corporal."),
                new Document("Carboidratos são a principal fonte de energia do organismo. Dividem-se em simples (açúcares de rápida absorção) e complexos (amidos e fibras, de absorção lenta). Fontes saudáveis incluem grãos integrais, batata doce, aveia, frutas e legumes. Devem representar entre 45% e 65% das calorias diárias em uma dieta equilibrada."),
                new Document("Gorduras são essenciais para absorção das vitaminas lipossolúveis (A, D, E e K), produção hormonal e proteção de órgãos vitais. Gorduras saudáveis incluem monoinsaturadas (azeite, abacate) e poli-insaturadas como ômega-3 (salmão, sardinha, linhaça). Evite gorduras trans. Devem representar 20% a 35% das calorias diárias."),
                new Document("Fibras alimentares são carboidratos não digeríveis que promovem saúde intestinal, controle glicêmico, redução do colesterol e saciedade prolongada. A ingestão diária recomendada é de 25 a 38 gramas. Fontes ricas em fibras: aveia, feijão, lentilha, grão-de-bico, frutas com casca e vegetais folhosos."),
                new Document("Vitaminas e minerais essenciais: Vitamina C (imunidade e colágeno) — frutas cítricas, acerola, pimentão. Vitamina D (ossos e imunidade) — exposição solar, peixes gordurosos. Vitamina B12 (sistema nervoso) — carnes, ovos, laticínios. Ferro (transporte de oxigênio) — carnes vermelhas, feijão, espinafre. Cálcio (ossos) — laticínios, couve, brócolis."),
                new Document("Hidratação adequada é fundamental para todas as funções do organismo, incluindo regulação térmica, transporte de nutrientes e função cognitiva. A recomendação geral é de 35ml por kg de peso corporal por dia. Atletas e pessoas em clima quente devem aumentar a ingestão. Sinais de desidratação: urina amarelo escuro, sede intensa, fadiga e tontura."),
                new Document("O IMC (Índice de Massa Corporal) é calculado dividindo o peso em kg pela altura em metros ao quadrado. Classificação: abaixo de 18.5 = abaixo do peso; 18.5-24.9 = eutrófico (normal); 25-29.9 = sobrepeso; 30-34.9 = obesidade grau I; 35-39.9 = obesidade grau II; 40 ou mais = obesidade grau III. O IMC não considera composição corporal, sendo limitado para atletas."),
                new Document("O prato saudável (Método do Prato) é uma forma prática de montar refeições equilibradas: 1/2 do prato com vegetais variados e coloridos; 1/4 com proteínas magras (frango, peixe, ovos, leguminosas); 1/4 com carboidratos complexos (arroz integral, batata doce, mandioca). Inclua uma fonte de gordura saudável como azeite ou abacate."),
                new Document("Para perda de peso saudável e sustentável, recomenda-se um déficit calórico moderado de 300 a 500 kcal/dia, visando perda de 0.5 a 1kg por semana. Dietas muito restritivas (abaixo de 1200 kcal para mulheres ou 1500 kcal para homens) causam perda muscular e efeito rebote. Combine alimentação equilibrada com prática regular de exercícios."),
                new Document("Para hipertrofia muscular (ganho de massa), é necessário superávit calórico de 200 a 300 kcal/dia com consumo proteico elevado (1.6 a 2.2g/kg/dia). Treino de resistência progressivo é indispensável. Refeições pré-treino (carboidratos + proteína) e pós-treino (proteína de rápida absorção + carboidratos) otimizam a recuperação e síntese muscular."),
                new Document("A dieta mediterrânea é considerada uma das mais saudáveis do mundo. Baseia-se no consumo abundante de vegetais, frutas, leguminosas, grãos integrais, azeite de oliva e peixes. Consumo moderado de laticínios e aves. Baixo consumo de carnes vermelhas e alimentos processados. Associada à redução do risco cardiovascular, diabetes tipo 2 e longevidade."),
                new Document("Alimentos ultraprocessados são produtos industriais com pouco ou nenhum alimento in natura, contendo aditivos como conservantes, corantes, aromatizantes e estabilizantes. Exemplos: refrigerantes, salgadinhos, biscoitos recheados, macarrão instantâneo. O consumo frequente está associado a obesidade, hipertensão, diabetes e doenças cardiovasculares. O Guia Alimentar Brasileiro recomenda evitá-los."),
                new Document("O jejum intermitente é um padrão alimentar com períodos alternados de jejum e alimentação. Os protocolos mais comuns são 16:8 (16h de jejum, 8h de alimentação) e 5:2 (5 dias normais, 2 dias com restrição severa). Pode auxiliar no controle calórico e sensibilidade à insulina. Não é recomendado para gestantes, crianças, diabéticos tipo 1 ou pessoas com histórico de transtornos alimentares sem supervisão médica.")
        );

        try {
            vectorStore.add(documents);
            log.info("Base de conhecimento nutricional carregada com {} documentos.", documents.size());
        } catch (Exception e) {
            log.warn("Nao foi possivel carregar a base RAG (verifique cota da API): {}", e.getMessage());
        }
    }
}
