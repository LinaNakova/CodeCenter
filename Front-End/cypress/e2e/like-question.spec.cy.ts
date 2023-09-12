describe('Select Card with Non-Zero .answers Value', () => {
  it('like a question', () => {
    cy.visit('http://localhost:4200');
    cy.get('#login').click();
    cy.url().should('include', '/login');
    cy.get('input[name="username"]').type('cypress.test');
    cy.get('input[name="password"]').type('cypress');
    cy.get('#login-btn').click();
    cy.url().should('include', '/top-questions');
    cy.get('.card').each(($card) => {
      cy.wrap($card).should('not.equal', $card.find('.answers').text())
      cy.wrap($card).contains('.answers', /^[1-9]\d*$/)
      cy.wrap($card).click();
      cy.url().should('include', '/question')
      let oldValue : string | number = ""
      cy.get("#numVotes").invoke('text').then(
        (text) => {
          // Store the text in the variable
          oldValue = text
        }
      )
      cy.get('.arrow:first').click()
      let newValue : string | number = ""
      cy.get("#numVotes").invoke('text').then(
        (text) => {
          // Store the text in the variable
          oldValue = text
        }
      )
      cy.wrap((+oldValue)).should('equal', (+newValue)+1);
      return false;
    });
  });
});
