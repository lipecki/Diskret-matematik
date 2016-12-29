
function edges = getEdges (matrix)
  [i,j,k] = find(matrix);
  weights = [i,j,k];
  sortedWeights = sortrows(weights,3);
  
  l = length(sortedWeights);
  n = 2;
  # i den sorterade listan är varannan kant en dublett
  while n <= l
    edges((n)/2,:) = sortedWeights(n,:);
    n += 2;
  end;
  
endfunction
