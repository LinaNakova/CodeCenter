describe('Register Test', () => {
  it('should login  ', () => {
    cy.visit('http://localhost:4200/register');
    cy.get('input[name="username"]').type('cypress.test');
    cy.get('input[name="name"]').type('cypress');
    cy.get('input[name="surname"]').type('cypress');
    cy.get('input[name="email"]').type('cypress@cypress.com');
    cy.get('input[name="password"]').type('cypress');
    cy.get('.btn:last').click();

    cy.get('#login').click();
    cy.url().should('include', '/login');
    cy.get('input[name="username"]').type('cypress.test');
    cy.get('input[name="password"]').type('cypress');
    cy.get('#login-btn').click();
    cy.url().should('include', '/top-questions');
  });
});
