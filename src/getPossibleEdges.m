

function nodeList = getPossibleEdges (edgeMatrix,weightedAdjM)
  #edgeMatrix
  nodeList = edgeMatrix(1,1:2);
  i = 1;
  l = rows(edgeMatrix);
  for i=1:l
    nodeList = union(nodeList,edgeMatrix(i,1:2));
  endfor;
  
  ## för varje nod, lista noderna som angränsar 
  #initiera listan för angränsande kanter
  adjacentEdges = [];
  #n = rows(adjacentEdges)
  
  #dra ut alla angränsande kanter från ursprungsmatrisen
  for i = 1:length(nodeList)
    [l,j,k] = find(weightedAdjM(nodeList(i),:));
    
    #lägg till de kanter som inte skapar en loop
    for m = 1:length(l)
      n = rows(adjacentEdges);
      
      # kontrollera att kantens andra nod inte redan finns i det uppspännande trädet
      yes = false;
      for o = 1:length(nodeList)
        if (j(m) == nodeList(o)) yes = true;
        endif;
      endfor;
      
      # lägg till kanten om den inte skapar en loop
      if (~yes) 
        adjacentEdges(n + 1,:) = horzcat(nodeList(i),j(m),k(m));
      endif;
      
    endfor;
  endfor;
  
  nodeList = sortrows(adjacentEdges,3);
endfunction
