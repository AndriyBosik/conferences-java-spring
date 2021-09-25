import CircularPreloader from "../CircularPreloader/CircularPreloader";

function LoadingPage() {
    return (
        <div className="full-width center-align">
            <div className="window-height s-vflex-center">
                <div className="s-hflex-center">
                    <CircularPreloader />
                </div>
            </div>
        </div>
    );
}

export default LoadingPage;