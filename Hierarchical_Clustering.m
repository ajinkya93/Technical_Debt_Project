%MATLAB script to apply hierarchical clustering on a given dataset.
%Author: Ajiinkkya Bhaalerao

File_Distance = pdist(BitcoinClusteringResults);
squareform(File_Distance);
Hierarchical_Clustering_Tree = linkage(File_Distance);
dendrogram(Hierarchical_Clustering_Tree) % Generates a dendrogram
c = cophenet(Hierarchical_Clustering_Tree,File_Distance);
I = inconsistent(Hierarchical_Clustering_Tree);
%Cluster_Index = cluster(Hierarchical_Clustering_Tree,'cutoff',1.0);
Cluster_Index = cluster(Hierarchical_Clustering_Tree,'maxclust',3);
silhouette(BitcoinClusteringResults,Cluster_Index) % Plots a silhoutte plot