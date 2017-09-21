%MATLAB script to apply hyper-geomteric distribution on clusters of Bitcoin and BitcoinXT projects to identify large, less-overlaaping clusters
% that aids in identification of hotspots of TD.
%Author: Ajiinkkya Bhaalerao

Bitcoin_Clusters = [1;1;2;1;39;1;2;1;1;1;2;1;5;11;2;1;1;1;1;2];
BitcoinXT_Clusters = [1;37;2;1;2;1;1;1;1;1;1;1;4;10;2;1;1;1;1;1];
total_no_of_files = 148;
no_of_files_to_draw = 1 ; %min of all clusters from both the projects
overlap_matrix = zeros(20,20);
count = 0;
for i = 1:20
    for j =1:20
        no_of_files_in_this_cluster = BitcoinXT_Clusters(j);
        p = hygecdf(no_of_files_to_draw,total_no_of_files,no_of_files_in_this_cluster,Bitcoin_Clusters(i));
        overlap_matrix(i,j) = p;
        count = count + 1;
    end
end
%overlap_matrix
[M,I] = min(overlap_matrix(:))
[I_row,I_col] = ind2sub(size(overlap_matrix),I)