#Code Workshop
library(mlbench)
library(corrplot)
library(tidyverse)
library(tidymodels)
data(BostonHousing)

#Let's explore the data first
BostonHousing %>%
  select_if(is.numeric) %>%
  select(1:4, medv) %>%
  plot()

BostonHousing %>%
  select_if(is.numeric) %>%
  select(5:9, medv) %>%
  plot()

BostonHousing %>%
  select_if(is.numeric) %>%
  select(10:13, medv) %>%
  plot()

#Corr matrix/plot
BostonHousing %>%
  select_if(is.numeric) %>%
  cor() %>%
  corrplot.mixed(order = "AOE") #AOE

#PCA
Housing_PCs <- BostonHousing %>%
                  select_if(is.numeric) %>%
                  dplyr::select(-medv) %>%
                  prcomp(center = T, scale = T)

Housing_PCs$x %>%
  as_tibble() %>%
  mutate(medv = BostonHousing$medv,
         chas = BostonHousing$chas) %>%
  ggplot(aes(x = PC1, y = PC2, color = medv)) +
    geom_point()

Housing_PCs$x %>%
  as_tibble() %>%
  mutate(medv = BostonHousing$medv,
         chas = BostonHousing$chas) %>%
  ggplot(aes(x = PC3, y = PC4, color = medv)) +
  geom_point()

#Scree Plot
summary(Housing_PCs)$importance %>%
  as_tibble(rownames = NA) %>%
  rownames_to_column() %>%
  pivot_longer(cols = 2:13) %>%
  pivot_wider(names_from = rowname) %>%
  mutate(name = factor(name, levels = name[order(-`Proportion of Variance`)])) %>%
  ggplot(aes(x = name, y = `Proportion of Variance`)) +
    geom_bar(stat = "identity", fill = "blue") +
    geom_line(group = 1, color = "orange", size = 2) +
    geom_point() +
    scale_y_continuous(labels = scales::percent)

#Trying with PCA regression
library(pls)

BostonHousing$medv <- scale(BostonHousing$medv)
pca_reg <- pcr(medv ~ ., data = BostonHousing, scale = TRUE)
df <- pca_reg$scores

comp_nums <- seq(2, ncol(df))

df2 <- df[,1]

for (num in comp_nums){
  df2 <- as.data.frame(cbind(df2, df[,num]))
}

colnames(df2) <- paste("Comp", seq(1,13))

preds <- unname(predict(pca_reg, type = "response", ncomp = 3)[,,1])

df2 <- df2 %>%
  mutate(medv = BostonHousing$medv, chas = BostonHousing$chas,
         preds = preds)

PCA_pred_plot <- df2 %>%
  ggplot() +
    geom_point(aes(x = `Comp 1`, y = medv, color = medv)) +
    geom_smooth(aes(x = `Comp 1`, y = preds)) +
    ylab("Comp 2") +
    xlab("Comp 1") +
    ggtitle("PCR") +
    theme(legend.position = "none")

PCA_pred_plot

#Trying with pls
reg_model <- plsr(medv ~ ., data = BostonHousing, scale = TRUE)

df3 <- reg_model$scores

comp_nums <- seq(2, ncol(df3))

df4 <- df3[,1]

for (num in comp_nums){
  df4 <- as.data.frame(cbind(df4, df3[,num]))
}

colnames(df4) <- paste("Comp", seq(1,13))

pls_preds <- predict(reg_model, type = "response", ncomp = 3)[,,1]

pls_pred_plot <- df4 %>%
  mutate(medv = BostonHousing$medv, chas = BostonHousing$chas,
         preds = pls_preds) %>%
  ggplot(aes(x = `Comp 1`, y = medv, color = medv)) +
    geom_point() +
    geom_smooth(aes(y = preds)) +
    ylab("Comp 2") +
    xlab("Comp 1") +
    ggtitle("PLSR") +
    theme(legend.position = "none")

pls_pred_plot

library(gridExtra)
grid.arrange(PCA_pred_plot, pls_pred_plot, ncol = 2)

#Next - metrics
df4 %>%
  mutate(medv = as.numeric(BostonHousing$medv), chas = BostonHousing$chas,
         preds = pls_preds) %>%
  metrics(estimate = preds, truth = medv)

df2 %>%
  mutate(medv = as.numeric(BostonHousing$medv), chas = BostonHousing$chas,
         preds = preds) %>%
  metrics(estimate = preds, truth = medv)


#LDA for Classification
url <- "http://archive.ics.uci.edu/ml/machine-learning-databases/breast-cancer-wisconsin/wdbc.data"
download.file(url, 'wdbc.csv')

columnNames <- c("id","diagnosis","radius_mean","texture_mean","perimeter_mean",
                 "area_mean","smoothness_mean","compactness_mean","concavity_mean",
                 "concave_points_mean","symmetry_mean","fractal_dimension_mean",
                 "radius_se","texture_se","perimeter_se","area_se","smoothness_se",
                 "compactness_se","concavity_se","concave_points_se","symmetry_se",
                 "fractal_dimension_se","radius_worst","texture_worst","perimeter_worst",
                 "area_worst","smoothness_worst","compactness_worst","concavity_worst",
                 "concave_points_worst","symmetry_worst","fractal_dimension_worst")

BreastCancer <- read_csv("wdbc.csv", col_types = NULL, col_names = columnNames)
View(BreastCancer)

BreastCancer <- BreastCancer %>% select(-id)

BreastCancer[,1:10] %>%
  plot()

BreastCancer[,c(1,11:20)] %>%
  plot()

BreastCancer[,c(1,21:31)] %>%
  plot()

BreastCancer[,2:31] %>%
  cor() %>%
  corrplot::corrplot()

library(MASS)
select <- dplyr::select

lda_model <- lda(diagnosis ~ ., data = BreastCancer)

lda_output <- predict(lda_model)

lda_output$x

BreastCancer$LDA <- as.numeric(lda_output$x)

LDA_plot <- BreastCancer %>%
  ggplot(aes(x = LDA, fill = diagnosis)) +
    geom_density() +
    ggtitle("LDA Projection")

AM_plot <- BreastCancer %>%
  ggplot(aes(x = area_mean, fill = diagnosis)) +
    geom_density() +
    ggtitle("Area Mean")

grid.arrange(LDA_plot, AM_plot)

BreastCancer <- BreastCancer %>%
  mutate(Index = as.numeric(row.names(BreastCancer)))

BreastCancer %>%
  ggplot(aes(x = Index, y = LDA, color = diagnosis)) +
    geom_point()

#Neural Networks (or "multi-layer perceptrons")
#Our LDA made things almost a bit too easy, so we're going to
#try a neural network on a more complicated arrangement:
BreastCancer %>%
  ggplot(aes(x = perimeter_mean, y = area_mean, color = diagnosis)) +
  geom_point()

BreastCancer$diagnosis <- as.factor(BreastCancer$diagnosis)

BC_split <- initial_split(BreastCancer)
BC_train <- training(BC_split)
BC_CV <- vfold_cv(BC_train, v = 5)

library(keras)
#install_keras() This will install keras (and the python packages associated with it, as well as tensorflow)

#Building our model
keras_engine <- mlp(mode = "classification",
                    hidden_units = tune(), #the number of units in the hidden layer
                    penalty = tune(), #L2 regularization
                    epochs = 20 #the default
                    ) %>%
                set_engine("keras") #The other option is "nnet", the default

keras_grid <- keras_engine %>%
                parameters() %>%
                grid_max_entropy(size = 10)

keras_wf <- workflow() %>%
              add_formula(diagnosis ~ perimeter_mean + area_mean) %>%
              add_model(keras_engine)

tuned_keras <- tune_grid(keras_wf, resamples = BC_CV, grid = keras_grid)

tuned_keras %>%
  collect_metrics() %>%
  ggplot(aes(x = hidden_units, y = mean)) +
    geom_line() +
    facet_wrap(. ~ .metric)

tuned_keras %>%
  collect_metrics() %>%
  ggplot(aes(x = penalty, y = mean)) +
  geom_line() +
  facet_wrap(. ~ .metric)

final_params <- tuned_keras %>%
  select_best(metric = "accuracy")

final_wf <- keras_wf %>%
  finalize_workflow(final_params)

#You might need to run this a few times to get the model to work -
#I'm not sure why this is the case. Sometimes it just makes flat predictions,
#Which it shouldn't do.
final_fit <- final_wf %>%
  last_fit(BC_split)

final_fit %>%
  collect_metrics()

final_predictions <- final_fit %>% collect_predictions()


BC_test <- testing(BC_split)

BC_test <- cbind(BC_test, final_predictions)
BC_test <- BC_test[,-39]

BC_test %>%
  mutate(diagnosis_bin = if_else(diagnosis == "M", 1, 0)) %>%
    ggplot(aes(x = area_mean, y = diagnosis_bin)) +
      geom_point() +
      geom_smooth(aes(y = .pred_M))

BC_test %>%
  mutate(diagnosis_bin = if_else(diagnosis == "M", 1, 0)) %>%
  ggplot(aes(x = perimeter_mean, y = diagnosis_bin)) +
  geom_point() +
  geom_smooth(aes(y = .pred_M))

BC_test %>%
  mutate(guesses = if_else(.pred_class == diagnosis, diagnosis, "Wrong Guess")) #%>%
  
BC_test$Tr_Fa <- BC_test$.pred_class == BC_test$diagnosis

BC_test$guesses <- NA

BC_test[BC_test$Tr_Fa == "TRUE",41] <- BC_test[BC_test$Tr_Fa == "TRUE",1]
BC_test$guesses

BC_test[BC_test$Tr_Fa == "FALSE",41] <- "Wrong Guess"
BC_test$guesses

BC_test %>%
  ggplot(aes(x = area_mean, y = perimeter_mean, color = guesses)) +
    geom_point()
