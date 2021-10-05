library(tidyverse)
library(MASS)
library(gridExtra)

str(diamonds)

diamonds %>%
  ggplot(aes(x=price)) +
  #Demo bins argument
    geom_histogram(bins = 50)

#Parameters (different probability density functions have different parameters)
norm_params <- as.list(fitdistr(diamonds$price, "normal")$estimate)
lognorm_params <- as.list(fitdistr(diamonds$price, "log-normal")$estimate)

# Checking out our theoretical normal curve
range(diamonds$price)
sample_range <- 326:18823

# Getting normal distribution predictions for the range of values in our data
sample_dist_norm <- dnorm(sample_range, mean=norm_params$mean, sd = norm_params$sd)
# Getting log-normal distribution predictions for the range of values in our data
sample_dist_lnorm <- dlnorm(sample_range, 
                            meanlog = lognorm_params$meanlog,
                            sdlog= lognorm_params$sdlog)

#Let's stick our data and our probability expectations into the same dataframe,
# So we can work with them more easily.
df <- tibble(range = sample_range,
             norm_probabilities = sample_dist_norm,
             lnorm_probabilities = sample_dist_lnorm)


#Setting up colors for our legend
colors <- c("Normal" = "red", "Log-Normal" = "blue", "Actual" = "green")
fills <- c("Normal" = "red", "Log-Normal" = "blue", "Actual" = "green")


#Plotting our theoretical curves (fitted to our actual data) 
ggplot() +
    geom_line(data=df, aes(x = range, y = norm_probabilities, color="Normal"), size= 1.5) +
    geom_line(data=df, aes(x=range, y=lnorm_probabilities, color="Log-Normal"), size=1.5) +
    geom_density(data= diamonds, aes(x=price, color="Actual"), size=1.5) +
    scale_color_manual(values = colors)

# Calculating cumulative probabilities (what percentage of the dataset falls below this value?)
cdf_norm <- pnorm(sample_range, norm_params$mean, norm_params$sd)
cdf_log <- plnorm(sample_range, lognorm_params$meanlog, lognorm_params$sdlog)

df$cumulative_probabilities_norm <- cdf_norm
df$cumulative_probabilities_log <- cdf_log

#Plotting probabilities with the cumulative density function (pnorm)
ggplot() +
  geom_line(data= df, aes(x= range, y= cumulative_probabilities_norm, color="Normal"), size=2) +
  geom_line(data= df, aes(x= range, y= cumulative_probabilities_log, color="Log-Normal"), size=2) +
  stat_ecdf(data= diamonds, aes(x=price, color="Actual"), size=2) +
  scale_color_manual(values = colors)

#So, if our theoretical curve resembles our true population,
# then a random sample from our theoretical population should look like our 
#sample, right? Well, that's easy to check.
length(diamonds$price)
test_sample_size = 1000
random_sample_norm <- rnorm(test_sample_size, norm_params$mean, norm_params$sd)
random_sample_lnorm <- rlnorm(test_sample_size, lognorm_params$meanlog, lognorm_params$sdlog)
random_df <- tibble(test_data_norm = random_sample_norm, test_data_log = random_sample_lnorm)

#Does our sampled data look like our actual data?
ggplot() +
  geom_density(data= random_df, aes(x=test_data_norm, fill="Normal"), alpha=0.4) +
  geom_density(data= random_df, aes(x=test_data_log, fill="Log-Normal"), alpha=0.4) +
  geom_density(data=diamonds, aes(x=price, fill="Actual"), alpha=0.4) +
  ggtitle("Comparing Sampled Data to Actual Data")


# So, we can get estimated probabilities from our data.
# Logically, we should be able to do the opposite too: Get data from probabilities.
prob_range <- seq(0,1, 0.01)
theoretical_quantiles_norm <- qnorm(prob_range, norm_params$mean, norm_params$sd)
theoretical_quantiles_log <- qlnorm(prob_range, lognorm_params$meanlog, lognorm_params$sdlog)
actual_quantiles <- quantile(diamonds$price, probs = seq(0,1, 0.01))

quantile_df <- tibble("Prob Sample" = prob_range,
                  "T_Quantiles_Norm" = theoretical_quantiles_norm,
                  "T_Quantiles_Log" = theoretical_quantiles_log,
                  "A_Quantiles" = actual_quantiles)


#Plotting quantiles with the (qnorm) function
quantile_df %>%
  ggplot(aes(x=`Prob Sample`)) +
  geom_point(aes(y=`T_Quantiles_Norm`, color="Normal")) +
  geom_point(aes(y=`T_Quantiles_Log`, color="Log-Normal")) +
  geom_point(aes(y=`A_Quantiles`, color="Actual"))

diamonds %>%
  ggplot() +
    geom_qq(aes(sample=price))
