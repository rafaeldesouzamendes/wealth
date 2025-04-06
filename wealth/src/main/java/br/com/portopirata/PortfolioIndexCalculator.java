package br.com.portopirata;
import java.util.*;

public class PortfolioIndexCalculator {

    static class Asset {
        String name;
        double weight;

        public Asset(String name, double weight) {
            this.name = name;
            this.weight = weight;
        }
    }

    public static double calculateIDC(List<Asset> assets, double[][] exchangeRates) {
        double idc = 0.0;
        int n = assets.size();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                double relativePrice = exchangeRates[i][j];  
                double logPriceDiff = Math.abs(Math.log(relativePrice));
                idc += assets.get(i).weight * assets.get(j).weight * logPriceDiff;
            }
        }
        return idc / (n * (n - 1));  
    }

    public static double calculateEntropy(List<Asset> assets) {
        double entropy = 0.0;
        for (Asset asset : assets) {
            if (asset.weight > 0) {
                entropy -= asset.weight * Math.log(asset.weight);
            }
        }
        return entropy;
    }

    public static double calculateAverageCorrelation(double[][] correlationMatrix) {
        int n = correlationMatrix.length;
        double sum = 0.0;
        int count = 0;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                sum += correlationMatrix[i][j];
                count++;
            }
        }
        return (count > 0) ? sum / count : 0;
    }

    public static double calculateIDR(List<Asset> assets, double[][] correlationMatrix) {
        double entropy = calculateEntropy(assets);
        double avgCorrelation = calculateAverageCorrelation(correlationMatrix);
        return entropy * (1 - avgCorrelation);
    }

    public static double calculateIER(List<Asset> assets, double[][] exchangeRates, double[][] correlationMatrix, double alpha, double beta) {
        double idc = calculateIDC(assets, exchangeRates);
        double idr = calculateIDR(assets, correlationMatrix);
        return alpha * (1 / idc) + beta * idr;
    }

    public static void main(String[] args) {
        List<Asset> assets = Arrays.asList(
            new Asset("BTC", 0.30),
            new Asset("USDT", 0.20),
            new Asset("BRL", 0.15),
            new Asset("COP", 0.10),
            new Asset("EUR", 0.15),
            new Asset("XAU", 0.10)  
        );

        double[][] exchangeRates = {
            {1.0, 50000, 250000, 200000000, 46000, 25},  
            {0.00002, 1.0, 5, 4000, 0.92, 0.0005},  
            {0.000004, 0.2, 1.0, 800, 0.18, 0.0001},  
            {0.000000005, 0.00025, 0.00125, 1.0, 0.00022, 0.000000125},  
            {0.000022, 1.08, 5.5, 4500, 1.0, 0.00054},  
            {0.04, 2000, 10000, 8000000, 1850, 1.0}  
        };

        double[][] correlationMatrix = {
            {1.00, 0.85, 0.65, 0.50, 0.75, 0.30},
            {0.85, 1.00, 0.60, 0.45, 0.70, 0.25},
            {0.65, 0.60, 1.00, 0.55, 0.40, 0.20},
            {0.50, 0.45, 0.55, 1.00, 0.35, 0.15},
            {0.75, 0.70, 0.40, 0.35, 1.00, 0.45},
            {0.30, 0.25, 0.20, 0.15, 0.45, 1.00}
        };

        double alpha = 0.5;
        double beta = 0.5;

        double idc = calculateIDC(assets, exchangeRates);
        double idr = calculateIDR(assets, correlationMatrix);
        double ier = calculateIER(assets, exchangeRates, correlationMatrix, alpha, beta);

        System.out.println("Índice de Disparidade Cambial (IDC): " + idc);
        System.out.println("Índice de Diversificação Relativa (IDR): " + idr);
        System.out.println("Índice de Estabilidade de Riqueza (IER): " + ier);
    }
}
