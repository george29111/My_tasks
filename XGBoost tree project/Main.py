import pandas as pd
import xgboost as xgb
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_absolute_error
from sklearn.preprocessing import StandardScaler
import sklearn.ensemble as ens
from xgboost import plot_importance
import matplotlib.pyplot as plt
import seaborn as sns


def train_predict_model(history_path ):  
    
    df = pd.read_excel(history_path)

    features = [ 'season', 'position_diff', 'won_diff', 'drawn_diff', 'lost_diff', 'gf_diff', 'ga_diff', 'gd_diff', 'points_diff', 'pts/mp_diff', 'xg_diff', 
                'xga_diff', 'xgd_diff', 'xgd/90_diff', 'attendence_diff', 'N of players_diff','age_diff','possesion_diff','Gls_diff','Sh_diff','SoT_diff',
                'SoT%_diff','Sh/90_diff','SoT/90_diff','G/Sh_diff','G/SoT_diff','Dist_diff','FK_diff','PK_diff','PKatt_diff','elo_rating_diff'] 
    X = df[features]
    y = (df['Result'] - 1) / (df['Result'].max() - 1)

    
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)

    X_train, X_test, y_train, y_test = train_test_split( X_scaled, y, train_size=0.85, random_state=42)



    model = xgb.XGBRegressor(
        n_estimators=300,
        subsample=0.9,
        random_state=42,
        learning_rate = 0.01,
        max_depth = 3,
        gamma = 1,
        reg_lambda = 5,   
        scale_pos_weight = 1
    )
    
    model.fit(X_train, y_train)

    y_prediction_test = model.predict(X_test)


    mae = mean_absolute_error(y_test, y_prediction_test)
    print(f"mean_absolute_error on tests: {mae:.2f}")


    plot_importance(model)
    plt.title("Importancy of the caracteristics")
    plt.show()
    


    plt.figure(figsize=(8, 6))
    sns.scatterplot(x=y_test, y=y_test, color='blue', label='Real posion')

    sns.scatterplot(x=y_test, y=y_prediction_test, color='orange', label='Guesed position')
    plt.xlabel("Real posion")
    plt.ylabel("Guesed")
    plt.title("Real vs Guesed posion")
    plt.grid(True)
    plt.legend()
    plt.show()




def train_Random_forrest(history_path):
  
    df = pd.read_excel(history_path)
    features = [ 
        'season', 'position_diff', 'won_diff', 'drawn_diff', 'lost_diff', 'gf_diff', 'ga_diff', 'gd_diff', 
        'points_diff', 'pts/mp_diff', 'xg_diff', 'xga_diff', 'xgd_diff', 'xgd/90_diff', 'attendence_diff',
        'N of players_diff', 'age_diff', 'possesion_diff', 'Gls_diff', 'Sh_diff', 'SoT_diff', 'SoT%_diff',
        'Sh/90_diff', 'SoT/90_diff', 'G/Sh_diff', 'G/SoT_diff', 'Dist_diff', 'FK_diff', 'PK_diff',
        'PKatt_diff', 'elo_rating_diff' 
    ]

    X = df[features]
    y = df['Result'] 


    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)


    X_train, X_test, y_train, y_test = train_test_split(X_scaled, y, train_size=0.85, random_state=42)

    model = ens.RandomForestRegressor(
        n_estimators=300,
        criterion='squared_error',
        max_depth=5,
        min_samples_split=2,
        min_samples_leaf=1,
        max_features='sqrt',
        bootstrap=True,
        random_state=42
    )
    model.fit(X_train, y_train)


    y_prediction_test = model.predict(X_test)


    mae = mean_absolute_error(y_test, y_prediction_test)
    print(f"mean_absolute_error on tests: {mae:.2f}")

    # 8. Визуализация на важността на характеристиките
    importances = model.feature_importances_
    feature_importance_df = pd.DataFrame({
        'Feature': features,
        'Importance': importances
    }).sort_values(by='Importance', ascending=False)

    plt.figure(figsize=(10, 8))
    sns.barplot(data=feature_importance_df, x='Importance', y='Feature')
    plt.title("Важност на характеристиките (Random Forest)")
    plt.tight_layout()
    plt.show()

    # 9. Графика: Истинска срещу Предсказана стойност
    plt.figure(figsize=(8, 6))
    sns.scatterplot(x=y_test, y=y_test, color='blue', label='Истинска стойност')
    sns.scatterplot(x=y_test, y=y_prediction_test, color='orange', label='Предсказана стойност')
    plt.xlabel("Истинска позиция")
    plt.ylabel("Предсказана позиция")
    plt.title("Истинска vs Предсказана позиция")
    plt.grid(True)
    plt.legend()
    plt.show()


if __name__ == "__main__":
    
    history_data_file = "The address of the file matches_with_results.xlsx"
   
    train_Random_forrest(history_data_file)
