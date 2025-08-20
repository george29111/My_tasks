import pandas as pd
import xgboost as xgb
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.model_selection import GridSearchCV
import sklearn.ensemble as ens


def params_for_xgboost(addres_path):
    df = pd.read_excel(addres_path)

    features = [ 'season', 'position_diff', 'won_diff', 'drawn_diff', 'lost_diff', 'gf_diff', 'ga_diff', 'gd_diff', 'points_diff', 'pts/mp_diff', 'xg_diff', 
                'xga_diff', 'xgd_diff', 'xgd/90_diff', 'attendence_diff', 'N of players_diff','age_diff','possesion_diff','Gls_diff','Sh_diff','SoT_diff',
                'SoT%_diff','Sh/90_diff','SoT/90_diff','G/Sh_diff','G/SoT_diff','Dist_diff','FK_diff','PK_diff','PKatt_diff','elo_rating_diff'] 
    X = df[features]
    y = df['Result'] 

    
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)


    X_train, y_train = train_test_split( X_scaled, y, train_size=0.85, random_state=42)

    param_grid = {
        'learning_rate': [0.03, 0.01],
        'max_depth': [3, 8],
        'n_estimators': [500, 300],
        'gamma': [0.25, 1],
        'reg_lambda': [10, 5],
        'scale_pos_weight': [5, 1]
    }

    base_model = xgb.XGBRegressor(
        subsample=0.9,
        random_state=42
    )

    grid_search = GridSearchCV(base_model, param_grid, cv=3, scoring='neg_mean_absolute_error')
    grid_search.fit(X_train, y_train)

    print(grid_search.best_params_)


## namirame nai-dobrite parametri za random forrest-a
def params_for_random_forrest(addres_path):
     
    df = pd.read_excel(addres_path)

    features = [ 'season', 'position_diff', 'won_diff', 'drawn_diff', 'lost_diff', 'gf_diff', 'ga_diff', 'gd_diff', 'points_diff', 'pts/mp_diff', 'xg_diff', 
                'xga_diff', 'xgd_diff', 'xgd/90_diff', 'attendence_diff', 'N of players_diff','age_diff','possesion_diff','Gls_diff','Sh_diff','SoT_diff',
                'SoT%_diff','Sh/90_diff','SoT/90_diff','G/Sh_diff','G/SoT_diff','Dist_diff','FK_diff','PK_diff','PKatt_diff','elo_rating_diff'] 
    X = df[features]
    y = df['Result'] 

    
    scaler = StandardScaler()
    X_scaled = scaler.fit_transform(X)


    X_train, y_train = train_test_split( X_scaled, y, train_size=0.85, random_state=42)


    param_grid = {
        'n_estimators': [300],
        'max_depth': [5],
        'min_samples_split': [2],
        'min_samples_leaf': [1],
        'max_features': ['sqrt'],
        'bootstrap'  :[True, ],
        'criterion': ['squared_error', ]
    }

    rf = ens.RandomForestRegressor(random_state=42)
    grid_search = GridSearchCV(rf, param_grid, cv=5, scoring='neg_mean_absolute_error', n_jobs=-1)
    grid_search.fit(X_train, y_train)

    print(f"Най-добри параметри: {grid_search.best_params_}")



if __name__ == "__main__":
    addres_path = "The address of the file matches_with_results.xlsx"
    params_for_random_forrest(addres_path)



   