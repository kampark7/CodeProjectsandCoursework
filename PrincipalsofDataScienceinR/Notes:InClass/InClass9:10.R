str(diamonds)

summary(diamonds$price)

sd(diamonds$price)
mad(diamonds$price)

diamonds %>%
  ggplot(aes(x=price)) +
    geom_histogram(bins = 50)