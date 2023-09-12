describe('Login Test', () => {
  it('should login successfully', () => {
    cy.visit('http://localhost:4200');
    cy.get('#login').click();
    cy.url().should('include', '/login');
    cy.get('input[name="username"]').type('cypress.test');
    cy.get('input[name="password"]').type('cypress');
    cy.get('#login-btn').click();
    cy.url().should('include', '/top-questions');
  });
});
