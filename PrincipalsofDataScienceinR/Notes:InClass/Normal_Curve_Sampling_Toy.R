# Setting our sample size
size <- 1000

#Setting the shape of our "population" curve
standard_deviation <- 20.627
mean_value <- 32.56

#Getting a sample from our "population"
plot_data <- tibble(rnorm(size, mean_value, standard_deviation))
#Renaming the column in our sample to be easier to work with
names(plot_data) <- "data"

#Plotting the data
plot_data %>%
  ggplot(aes(x=data)) +
  geom_density(fill="pink") +
  #Plotting our exact 'population' curve over our data
  stat_function(fun=dnorm, args=list(mean= mean_value,
                                     sd=standard_deviation))

#Does the random sample data fit the calculated normal curve? What happens if you change the sample size?