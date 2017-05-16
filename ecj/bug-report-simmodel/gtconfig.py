git_home = ""
all_issues_csv = "/Users/bobbybruce/Desktop/bugzilla-data-miner/ecj/Release_Counter_UNFILTERED.csv"

gambit_folder = "/Volumes/Gambit/Gambit.app/Contents/MacOS/"
enumerate_equilibria_solver = "gambit-enummixed"
quantal_response_solver = "gambit-logit"

replications_per_profile = 30
report_stream_batching = True
simple_reporting_model = False

#Only for testing
parallel = True
parallel_blocks = 4

do_gatekeeper = True
success_rates = [1.0, 0.9]

do_throttling = False
inflation_factors = [0.01, 0.03, 0.05]

is_windows = False

# This parameters are only used in the experiments module (penaltyexp)
use_empirical_strategies = False
use_heuristic_strategies = True
