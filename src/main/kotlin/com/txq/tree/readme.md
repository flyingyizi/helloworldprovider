
- insert 表示

$$
insert(T,k)=\begin{cases}
node(\Phi,k,\Phi) : & T=\Phi   \\
node(insert(T_l,k),k',T_r)  : & k < k' \\
node(T_l,k',insert(T_r,k)) : & other   
\end{cases}
$$
