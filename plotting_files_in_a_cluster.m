%MATLAB script to plot total work done in all the intervals.
%Author: Ajiinkkya Bhaalerao

figure
% for i = 1:39
%     hold on %Redundant
%     plot(BitcoinClusteringResultsHC20(i,:))
%     hold on
% end

for j = 1:37
    hold on %Redundant
    plot(BitcoinXTClusteringResultsHC20(j,:))
    hold on
end