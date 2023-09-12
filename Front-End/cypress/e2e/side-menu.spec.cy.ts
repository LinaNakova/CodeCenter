describe('Loginpage', () => {
  it('should visit the loginpage', () => {
    cy.visit('http://localhost:4200');
    cy.get('a[href="/questions"]').click()
    cy.url().should('include', '/questions');
    cy.get('a[href="/tags"]').click()
    cy.url().should('include', '/tags');
    cy.get('a[href="/users"]').click()
    cy.url().should('include', '/users');
  });
});
