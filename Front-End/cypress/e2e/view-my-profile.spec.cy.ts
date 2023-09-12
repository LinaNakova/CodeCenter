describe('Loginpage', () => {
  it('should visit the users page and view the first profile from the list', () => {
    cy.visit('http://localhost:4200');
    cy.get('#login').click();
    cy.url().should('include', '/login');
    cy.get('input[name="username"]').type('cypress.test');
    cy.get('input[name="password"]').type('cypress');
    cy.get('#login-btn').click();
    cy.url().should('include', '/top-questions');
    cy.get("#username").click({force: true})
    cy.url().should('include', '/profile');
    cy.get("#asked-questions").should('contain.text', 'Asked Questions');
    cy.get("#answers-for-questions").should('contain.text', 'Answers For Questions');
    cy.get("#mentioned-tags").should('contain.text', 'Tags Mentioned In Asked Questions');
  });
});
